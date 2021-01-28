package rhett.pezzuti.dailydose.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.database.UserPreferencesDao
import rhett.pezzuti.dailydose.viewmodels.PreferencesViewModel
import rhett.pezzuti.dailydose.viewmodels.SetupNameViewModel

class SetupNameViewModelFactory(
    private val datasource: UserPreferencesDao,
    private val app: Application,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetupNameViewModel::class.java)) {
            return SetupNameViewModel(datasource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}