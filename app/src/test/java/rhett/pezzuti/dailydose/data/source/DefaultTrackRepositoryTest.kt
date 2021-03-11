package rhett.pezzuti.dailydose.data.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    /** FakeDataSource is used for both dataSources, since the repository is the focus **/
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

    @ExperimentalCoroutinesApi
    @Test
    fun getTracks_requestAllTracksFromLocalDataSource() = runBlockingTest {
        // repo.getAllTracks() just returns from the local data source.
        val data = trackRepository.getAllTracks()

        assertThat(data, IsEqual(localTracks))

    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTracks_requestOneTrackFromLocalDataSource() = runBlockingTest {
        val data1 = trackRepository.getTrack(123456L)
        assertThat(data1, IsEqual(track1))

        val data2 = trackRepository.getTrack(1234567L)
        assertThat(data2, IsEqual(track2))

        val data3 = trackRepository.getTrack(12345678L)
        assertThat(data3, IsEqual(track3))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTracks_requestFavoritesFromLocalDataSource() = runBlockingTest {
        // getFavorites returns from localDataSource
        val favorites = trackRepository.getFavorites()
        assertThat(favorites, IsEqual(emptyList()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTracks_requestFavoritesFromRemoteDataSource() = runBlockingTest {
        val favorites = trackRepository.getFavorites()
        assertThat(favorites, IsEqual(track3))
    }

}