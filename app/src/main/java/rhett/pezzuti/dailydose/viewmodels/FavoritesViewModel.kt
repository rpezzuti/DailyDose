package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.UserPreferencesDao
import timber.log.Timber

class FavoritesViewModel(
    val trackDatabase: TrackDatabaseDao,
    val userDatabase: UserPreferencesDao,
    app: Application) : AndroidViewModel(app) {





    val tracks = trackDatabase.getFavorites(true)






    fun addToFavorites(url: String) {
        viewModelScope.launch {
            favorite(url)
        }
    }

    private suspend fun favorite(url: String) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(url)
            track.favorite = true
            trackDatabase.update(track)
        }
    }

    fun removeFromFavorites(url: String) {
        viewModelScope.launch {
            unFavorite(url)
        }
    }

    private suspend fun unFavorite(url: String) {
        withContext(Dispatchers.IO){
            val track = trackDatabase.getTrack(url)
            track.favorite = false
            trackDatabase.update(track)
        }
    }

}