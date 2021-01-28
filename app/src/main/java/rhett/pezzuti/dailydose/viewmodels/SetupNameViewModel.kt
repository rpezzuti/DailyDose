package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.database.User
import rhett.pezzuti.dailydose.database.UserPreferencesDao

class SetupNameViewModel(
    val userDatabase: UserPreferencesDao,
    application: Application
) : AndroidViewModel(application) {


    private val _navigatePreferences = MutableLiveData<Boolean>()
    val navigatePreferences : LiveData<Boolean>
        get() = _navigatePreferences


    init {
        _navigatePreferences.value = false
    }

    fun navigatePreferences() {
        _navigatePreferences.value = true
    }

    fun doneNavigatingPreferences() {
        _navigatePreferences.value = false
    }

    fun createNewUser(username: String) {
        viewModelScope.launch {
            insertUser(username)
        }
    }

    private suspend fun insertUser(username: String) {
        withContext(Dispatchers.IO) {
            val newUser = User(username)
            userDatabase.insert(newUser)
        }
    }
}