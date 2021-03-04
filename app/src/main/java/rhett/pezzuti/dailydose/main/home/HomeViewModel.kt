package rhett.pezzuti.dailydose.main.home

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.source.local.getInstance
import timber.log.Timber

class HomeViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {

    /** AndroidViewModel provides APPLICATION CONTEXT, while regular ViewModel() DOES NOT **/

    private val database = getInstance(getApplication())
    private val trackRepository = DefaultTrackRepository(database)

    init {
        Timber.i("homeViewModel Init block")

        viewModelScope.launch {
            // defaultTrackRepository.refreshTestTracks()
        }
    }

    val tracks = trackRepository.recentTracks

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