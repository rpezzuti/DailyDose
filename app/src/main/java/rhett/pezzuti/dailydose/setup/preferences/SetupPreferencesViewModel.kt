package rhett.pezzuti.dailydose.setup.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupPreferencesViewModel : ViewModel() {

    private val _donePreferences = MutableLiveData<Boolean>()
    val donePreferences : LiveData<Boolean>
        get() = _donePreferences

    init {
        _donePreferences.value = false
    }

    fun finishedPreferences(){
        _donePreferences.value = true
    }

    fun doneFinishing() {
        _donePreferences.value = false
    }
}