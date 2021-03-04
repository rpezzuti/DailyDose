package rhett.pezzuti.dailydose.main.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.asDomainModel

class FavoritesViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application) : AndroidViewModel(app) {





    val tracks = Transformations.map(trackDatabase.getFavorites(true)){
        it.asDomainModel()
    }






    fun addToFavorites(timestamp: Long) {
        viewModelScope.launch {
            favorite(timestamp)
        }
    }

    private suspend fun favorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(timestamp)
            track.favorite = true
            trackDatabase.update(track)
        }
    }

    fun removeFromFavorites(timestamp: Long) {
        viewModelScope.launch {
            unFavorite(timestamp)
        }
    }

    private suspend fun unFavorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(timestamp)
            track.favorite = false
            trackDatabase.update(track)
        }
    }

}