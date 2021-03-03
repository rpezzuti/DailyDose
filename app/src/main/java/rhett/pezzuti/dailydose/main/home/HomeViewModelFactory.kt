package rhett.pezzuti.dailydose.main.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao

class HomeViewModelFactory(
    private val trackDataSource: TrackDatabaseDao,
    private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(trackDataSource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}