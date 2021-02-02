package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.UserPreferencesDao
import timber.log.Timber

class FavoritesViewModel(
    val trackDatabase: TrackDatabaseDao,
    val userDatabase: UserPreferencesDao,
    app: Application) : AndroidViewModel(app) {












    fun addToFavorites(track: DatabaseTrack) {
        Timber.i("clicked")
        
    }

}