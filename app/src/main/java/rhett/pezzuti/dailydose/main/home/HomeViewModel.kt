package rhett.pezzuti.dailydose.main.home

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository
import timber.log.Timber
import kotlin.random.Random

class HomeViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {

    /** AndroidViewModel provides APPLICATION CONTEXT, while regular ViewModel() DOES NOT **/
    // If we dont need the context, we can just use a regular viewModel


    init {
        Timber.i("homeViewModel Init block")

        // Proof that it works lol
        viewModelScope.launch {
            trackRepository.addTrack(Track(
                4329328952L,
                "https://www.youtube.com",
                "Title",
                "Artist",
                "dubstep",
                "image",
                true
            ))
        }
    }


    val tracks = trackRepository.observeRecent()

    fun addToFavorites(timestamp: Long) {
        viewModelScope.launch {
            favorite(timestamp)
        }
    }

    private suspend fun favorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackRepository.getTrack(timestamp)
            track.favorite = true
            trackRepository.updateTrack(track)
        }
    }

    fun removeFromFavorites(timestamp: Long) {
        viewModelScope.launch {
            unFavorite(timestamp)
        }
    }

    private suspend fun unFavorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackRepository.getTrack(timestamp)
            track.favorite = false
            trackRepository.updateTrack(track)
        }
    }


}