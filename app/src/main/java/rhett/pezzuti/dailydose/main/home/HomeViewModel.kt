package rhett.pezzuti.dailydose.main.home

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.source.TrackRepository

class HomeViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {

    /** AndroidViewModel provides APPLICATION CONTEXT, while regular ViewModel() DOES NOT **/
    // If we dont need the context, we can just use a regular viewModel


    init {

    }


    var tracks = trackRepository.observeRecent()

    fun refreshTracks() {
        tracks = trackRepository.observeRecent()
    }

    fun addToFavorites(timestamp: Long) {
        viewModelScope.launch {
            favorite(timestamp)
        }
    }

    private suspend fun favorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            trackRepository.favoriteTrack(timestamp)
        }
    }

    fun removeFromFavorites(timestamp: Long) {
        viewModelScope.launch {
            unFavorite(timestamp)
        }
    }

    private suspend fun unFavorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            trackRepository.unFavoriteTrack(timestamp)
        }
    }


}