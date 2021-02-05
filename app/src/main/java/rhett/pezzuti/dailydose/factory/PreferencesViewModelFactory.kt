package rhett.pezzuti.dailydose.factory

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.viewmodels.PreferencesViewModel

class PreferencesViewModelFactory(
    private val app: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}