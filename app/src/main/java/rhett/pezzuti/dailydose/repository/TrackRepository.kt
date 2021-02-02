package rhett.pezzuti.dailydose.repository

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import rhett.pezzuti.dailydose.database.ClientDatabase
import rhett.pezzuti.dailydose.database.asDomainModel
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.network.BrowseFirebaseApi
import rhett.pezzuti.dailydose.network.asDatabaseModel

class TrackRepository (private val database: ClientDatabase) {


    // Public access of all the tracks in the database.
    // Fetching all the DATABASE tracks and returning them as DOMAIN tracks
    val tracks: LiveData<List<Track>> = Transformations.map(database.trackDatabaseDao.getAllTracks()) {
        it.asDomainModel()
    }


    suspend fun refreshTracks() {
        withContext(Dispatchers.IO) {
            // Get all tracks from Firebase
            val playlist = BrowseFirebaseApi.retrofitService.refreshDatbase().await()
            database.trackDatabaseDao.insertAll(*playlist.asDatabaseModel())
        }
    }



}