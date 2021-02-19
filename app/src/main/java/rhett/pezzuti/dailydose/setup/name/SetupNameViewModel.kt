package rhett.pezzuti.dailydose.setup.name

import androidx.lifecycle.*

class SetupNameViewModel() : ViewModel() {


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