package rhett.pezzuti.dailydose.main.home

import org.junit.After
import org.junit.Before
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.FakeTrackRepository

class HomeViewModelTest {


    private lateinit var trackRepository: FakeTrackRepository

    /** Subject Under Test **/
    private lateinit var homeViewModel: HomeViewModel




    @Before
    fun initViewModel() {

        trackRepository = FakeTrackRepository()

        val track1 = Track(
            123456L,
            "https://www.youtube.com",
            "Youtube Title",
            "Youtube Artist",
            "Dubstep",
            "IMAGE",
            false
        )

        val track2 = Track(
            1234567L,
            "https://soundcloud.com/",
            "Soundcloud Title",
            "Soundcloud Artist",
            "ambient-bass",
            "IMAGE",
            false
        )

        val track3 = Track(
            12345678L,
            "https://www.spotify.com/us/home/",
            "Spotify Title",
            "Spotify Artist",
            "metalcore",
            "IMAGE",
            true
        )
        trackRepository.addTracks(track1, track2, track3)

        homeViewModel = HomeViewModel(trackRepository)
    }

    @After
    fun cleanupDb() {
        trackRepository.cleanData()
    }




}