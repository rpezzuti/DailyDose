package rhett.pezzuti.dailydose.setup.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SetupNameViewModelFactory(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetupNameViewModel::class.java)) {
            return SetupNameViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}