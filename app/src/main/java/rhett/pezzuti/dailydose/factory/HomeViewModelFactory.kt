package rhett.pezzuti.dailydose.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.UserPreferencesDao
import rhett.pezzuti.dailydose.viewmodels.HomeViewModel

class HomeViewModelFactory(
    private val trackDataSource: TrackDatabaseDao,
    private val userDataSource: UserPreferencesDao,
    private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(trackDataSource, userDataSource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }











}