package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import rhett.pezzuti.dailydose.database.*
import rhett.pezzuti.dailydose.repository.TrackRepository
import timber.log.Timber

class HomeViewModel(
    val trackDatabase: TrackDatabaseDao,
    val userDatabase: UserPreferencesDao,
    app: Application) : AndroidViewModel(app) {

    /** Encapsulated LiveData **/
    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent : LiveData<Boolean>
        get() = _showSnackBarEvent


    val tracks = trackDatabase.getRecentTracks()

    private val database = getInstance(getApplication())
    private val trackRepository = TrackRepository(database)


    private val currentUser = MediatorLiveData<User>()
    fun getCurrentUser() = currentUser

    init {
        Timber.i("homeViewModel Init block")
        _showSnackBarEvent.value = false
        currentUser.addSource(userDatabase.getCurrentUser(), currentUser::setValue)


/*        viewModelScope.launch {
            trackRepository.refreshTracks()
        }
        val allTracks = trackRepository.tracks*/

    }


    /** Snackbar Event **/
    fun doneShowingSnackBar(){
        _showSnackBarEvent.value = false
    }


    fun onClear() {
        viewModelScope.launch {
            clear()
            _showSnackBarEvent.value = true
        }
    }

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
        withContext(Dispatchers.IO){
            val track = trackDatabase.getTrack(url)
            track.favorite = false
            trackDatabase.update(track)
        }
    }



}