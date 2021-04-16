package rhett.pezzuti.dailydose.main.browse

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks

enum class BrowseStatus { LOADING, ERROR, DONE}

class BrowseViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {

    private val _status = MutableLiveData<BrowseStatus>()
    val status: LiveData<BrowseStatus>
        get() = _status

    private val _playlist = MutableLiveData<List<Track>>()
    val playlist : LiveData<List<Track>>
        get() = _playlist

    private val _forceRefresh = MutableLiveData<Boolean>(true)

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

    val tracks : LiveData<List<Track>> = _tracks


    init {
        _status.value = BrowseStatus.LOADING
        // getTracksFromFirebase()
    }

    /** Exposed track playlist for fragment **/
    // val tracks : LiveData<List<Track>> = _tracks

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