package rhett.pezzuti.dailydose.main.browse

import androidx.lifecycle.*
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository
import timber.log.Timber

enum class BrowseStatus { LOADING, ERROR, DONE}

class BrowseViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {

    private val _status = MutableLiveData<BrowseStatus>()
    val status: LiveData<BrowseStatus>
        get() = _status

    private val _filter = MutableLiveData<String>()
    val filter : LiveData<String>
        get() = _filter

    private val _forceRefresh = MutableLiveData(true)

    private var firebaseResultCache : Flow<PagingData<Track>>? = null

    private var _tracks = _forceRefresh.switchMap { forceRefresh ->
        if (forceRefresh) {
            _status.value = BrowseStatus.LOADING
            viewModelScope.launch {
                trackRepository.refreshTracks()
                _status.value = BrowseStatus.DONE
            }
        }
        trackRepository.observeAllTracks()
    }

    /** Exposed track playlist for fragment **/
    val tracks : LiveData<List<Track>> = _tracks

    /** Attempt at chip filtering **/
    private val _playlist = MutableLiveData<List<Track>>()
    // val playlist = trackRepository.observeAllTracks()
    val playlist : LiveData<List<Track>>
        get() = _playlist

    // I need to change the value for the observer to be triggered
    // If i try and set the value for _playlist, I need to use a coroutine.
    // If i use a coroutine, then it tells me that I cant access the database on the main thread
    // If i put it on the background thread, then it tells me I can't set the value.

    init {

        _filter.value = "dubstep"
        _status.value = BrowseStatus.LOADING

    }


    private suspend fun filterTracks(genre: String) {
        withContext(Dispatchers.IO) {
            _tracks = trackRepository.observeGenre(genre)
        }
    }





    /** Database Functions **/
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