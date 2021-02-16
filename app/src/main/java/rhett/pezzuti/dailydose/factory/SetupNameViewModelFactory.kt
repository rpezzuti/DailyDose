package rhett.pezzuti.dailydose.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.viewmodels.SetupNameViewModel

class SetupNameViewModelFactory(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetupNameViewModel::class.java)) {
            return SetupNameViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}