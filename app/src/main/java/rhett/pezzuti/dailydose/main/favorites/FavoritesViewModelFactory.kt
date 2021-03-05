package rhett.pezzuti.dailydose.main.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.TrackRepository
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao

class FavoritesViewModelFactory(
    private val trackRepository: TrackRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(trackRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}