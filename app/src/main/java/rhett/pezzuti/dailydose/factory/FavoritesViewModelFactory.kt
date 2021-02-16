package rhett.pezzuti.dailydose.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.viewmodels.FavoritesViewModel

class FavoritesViewModelFactory(
    private val trackDataSource: TrackDatabaseDao,
    private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(trackDataSource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}