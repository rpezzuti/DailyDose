package rhett.pezzuti.dailydose.main.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.source.TrackRepository

class FavoritesViewModel(private val trackRepository: TrackRepository) : ViewModel() {

    val tracks = trackRepository.observeFavorites()

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