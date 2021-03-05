package rhett.pezzuti.dailydose.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.data.source.TrackRepository

class HomeViewModelFactory(
    private val trackRepository: TrackRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(trackRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}