package rhett.pezzuti.dailydose.main.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao

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