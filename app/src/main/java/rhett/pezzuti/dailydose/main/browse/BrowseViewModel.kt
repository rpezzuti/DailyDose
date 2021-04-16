package rhett.pezzuti.dailydose.main.browse

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository

/**
 * Enum status for progress bar and connection error visibility.
 */
enum class BrowseStatus { LOADING, ERROR, DONE}

class BrowseViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {


    /**
     * Encapsulated data for visibility of view elements related to loading status.
     */
    private val _status = MutableLiveData<BrowseStatus>()
    val status: LiveData<BrowseStatus>
        get() = _status

    private val _forceRefresh = MutableLiveData(true)

    /**
     * Forces a refresh through the network. Syncs remote data if found into database. Queries tracks from database after refresh & sync.
     *
     * Must be done this way to allow parameter to be initialized with data from the database without running into threading issue.
     */
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

    /**
     * Exposed LiveData to browseFragment to be put into the adapter.
     */
    val tracks : LiveData<List<Track>> = _tracks

    init {
        // Must be done because this status variable is NOT nullable.
        _status.value = BrowseStatus.LOADING
    }

    /** Database Methods **/
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