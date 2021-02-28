package rhett.pezzuti.dailydose.main.preferences

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.databinding.FragmentPreferencesBinding

class PreferencesViewModelFactory(
    private val app: Application,
    private val binding: FragmentPreferencesBinding
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(app, binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}