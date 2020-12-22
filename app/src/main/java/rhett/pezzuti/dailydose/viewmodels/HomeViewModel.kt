package rhett.pezzuti.dailydose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    /** Encapsulated LiveData **/
    private val _eventFavorites = MutableLiveData<Boolean>()
    val eventFavorites : LiveData<Boolean>
        get() = _eventFavorites

    init {
        _eventFavorites.value = false
    }

    fun navigateToFavorites(){
        _eventFavorites.value = true
    }

    fun doneNavigatingFavorites(){
        _eventFavorites.value = false
    }


}