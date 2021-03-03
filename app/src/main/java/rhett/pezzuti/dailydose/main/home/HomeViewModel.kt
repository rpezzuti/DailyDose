package rhett.pezzuti.dailydose.main.home

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.*
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.source.local.getInstance
import timber.log.Timber

class HomeViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {

    private val database = getInstance(getApplication())
    private val trackRepository = DefaultTrackRepository(database)


    init {
        Timber.i("homeViewModel Init block")

        viewModelScope.launch {
            // defaultTrackRepository.refreshTestTracks()
        }
    }

    val tracks = trackRepository.recentTracks

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            trackDatabase.clearAll()
        }
    }

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
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(url)
            track.favorite = false
            trackDatabase.update(track)
        }
    }


}