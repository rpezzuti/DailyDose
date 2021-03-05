package rhett.pezzuti.dailydose.main.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.TrackRepository

class BrowseViewModelFactory(
    private val trackRepository: TrackRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            return BrowseViewModel(trackRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}