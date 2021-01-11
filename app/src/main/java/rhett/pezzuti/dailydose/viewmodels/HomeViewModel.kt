package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import timber.log.Timber

class HomeViewModel(
    val database: TrackDatabaseDao,
    app: Application) : AndroidViewModel(app) {

    /** Encapsulated LiveData **/
    private val _eventFavorites = MutableLiveData<Boolean>()
    val eventFavorites : LiveData<Boolean>
        get() = _eventFavorites

    private val _eventUpload = MutableLiveData<Boolean>()
    val eventUpload : LiveData<Boolean>
        get() = _eventUpload


    val tracks = database.getRecentTracks()

    init {
        Timber.i("homeViewModel Init block")
        _eventFavorites.value = false
        _eventUpload.value = false
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


}