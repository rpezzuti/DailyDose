package rhett.pezzuti.dailydose.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.viewmodels.FavoritesViewModel

class FavoritesViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}