package rhett.pezzuti.dailydose.data.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import rhett.pezzuti.dailydose.data.Track

class DefaultTrackRepositoryTest {
    private val track1 = Track(
        123456L,
        "https://www.youtube.com",
        "Youtube Title",
        "Youtube Artist",
        "Dubstep",
        "IMAGE",
        false
    )

    private val track2 = Track(
        1234567L,
        "https://soundcloud.com/",
        "Soundcloud Title",
        "Soundcloud Artist",
        "ambient-bass",
        "IMAGE",
        false
    )

    private val track3 = Track(
        12345678L,
        "https://www.spotify.com/us/home/",
        "Spotify Title",
        "Spotify Artist",
        "metalcore",
        "IMAGE",
        true
    )

    private val localTracks = listOf(track1, track2).sortedBy { it.timestamp }
    private val remoteTracks = listOf(track2, track3).sortedBy { it.timestamp }

    private lateinit var tracksLocalDataSource: FakeDataSource
    private lateinit var tracksRemoteDataSource: FakeDataSource

    /** Class Under Test **/
    private lateinit var trackRepository: DefaultTrackRepository

    @Before
    fun initRepository() {
        tracksLocalDataSource = FakeDataSource(localTracks.toMutableList())
        tracksRemoteDataSource = FakeDataSource(remoteTracks.toMutableList())

        trackRepository = DefaultTrackRepository(
            tracksLocalDataSource, tracksRemoteDataSource,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun getTracks_requestAllTracksFromLocalDataSource() = runBlocking {
        // repo.getAllTracks() just returns from the local data source.
        val data = trackRepository.getAllTracks()

        assertThat(data, IsEqual(localTracks))

    }

}