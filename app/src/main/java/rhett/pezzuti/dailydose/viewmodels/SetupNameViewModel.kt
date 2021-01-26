package rhett.pezzuti.dailydose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupNameViewModel : ViewModel() {

    private val _navigatePreferences = MutableLiveData<Boolean>()
    val navigatePreferences : LiveData<Boolean>
        get() = _navigatePreferences


    init {
        _navigatePreferences.value = false
    }

    fun navigatePreferences() {
        _navigatePreferences.value = true
    }

    fun doneNavigatingPreferences() {
        _navigatePreferences.value = false
    }

}