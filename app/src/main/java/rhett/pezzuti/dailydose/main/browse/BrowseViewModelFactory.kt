package rhett.pezzuti.dailydose.main.browse

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao

class BrowseViewModelFactory(
    private val trackDataSource: TrackDatabaseDao,
    private val app: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            return BrowseViewModel(trackDataSource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}