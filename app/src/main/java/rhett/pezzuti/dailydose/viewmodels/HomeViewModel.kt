package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.User
import rhett.pezzuti.dailydose.database.UserPreferencesDao
import timber.log.Timber

class HomeViewModel(
    val trackDatabase: TrackDatabaseDao,
    val userDatabase: UserPreferencesDao,
    app: Application) : AndroidViewModel(app) {

    /** Encapsulated LiveData **/
    private val _eventFavorites = MutableLiveData<Boolean>()
    val eventFavorites : LiveData<Boolean>
        get() = _eventFavorites

    private val _eventUpload = MutableLiveData<Boolean>()
    val eventUpload : LiveData<Boolean>
        get() = _eventUpload

    private val _eventPreferences = MutableLiveData<Boolean>()
    val eventPreferences : LiveData<Boolean>
        get() = _eventPreferences

    private val _eventBrowse = MutableLiveData<Boolean>()
    val eventBrowse : LiveData<Boolean>
        get() = _eventBrowse

    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent : LiveData<Boolean>
        get() = _showSnackBarEvent


    val tracks = trackDatabase.getRecentTracks()


    private val currentUser = MediatorLiveData<User>()
    fun getCurrentUser() = currentUser

    init {
        Timber.i("homeViewModel Init block")
        _eventFavorites.value = false
        _eventUpload.value = false
        _eventPreferences.value = false
        _eventBrowse.value = false
        _showSnackBarEvent.value = false
        currentUser.addSource(userDatabase.getCurrentUser(), currentUser::setValue)
    }

    fun navigateToFavorites(){
        Timber.i("homeView Model called")
        _eventFavorites.value = true
    }

    fun doneNavigatingFavorites(){
        _eventFavorites.value = false
    }

    fun navigateToUpload(){
        Timber.i("homeView Model called")
        _eventUpload.value = true
    }

    fun doneNavigatingUpload(){
        _eventUpload.value = false
    }

    fun navigateToPreferences(){
        _eventPreferences.value = true
    }

    fun doneNavigatingPreferences(){
        _eventPreferences.value = false
    }

    fun navigateToBrowse(){
        _eventBrowse.value = true
    }

    fun doneNavigatingBrowse(){
        _eventBrowse.value = false
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

    fun onFavorite(url: String) {
        viewModelScope.launch {
            favorite(url)
        }
    }

    private suspend fun favorite(url: String) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(url)
            track.favorite = true
            trackDatabase.favorite(track)
        }
    }



}