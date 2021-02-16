package rhett.pezzuti.dailydose.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.database.ClientDatabase
import rhett.pezzuti.dailydose.database.asDomainModel
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.asDatabaseModel
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.network.BrowseFirebaseMoshi
import rhett.pezzuti.dailydose.network.asDatabaseModel
import rhett.pezzuti.dailydose.utils.asDatabaseModel


class TrackRepository(private val database: ClientDatabase) {


    // Public access of all the tracks in the database.
    // Fetching all the DATABASE tracks and returning them as DOMAIN tracks
    val tracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getAllTracks())
        {
            it.asDomainModel()
        }

    val recentTracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getRecentTracks())
        {
            it.asDomainModel()
        }

    // val favorites: LiveData<List<Track>>()


    suspend fun refreshTracks() {
        withContext(Dispatchers.IO) {
            // Get all tracks from Firebase
            val playlist = BrowseFirebaseMoshi.retrofitService.refreshDatabase().await()
            database.trackDatabaseDao.insertAll(*playlist.asDatabaseModel())
        }
    }

    suspend fun refreshTestTracks() {
        withContext(Dispatchers.IO) {
            val playlist = BrowseFirebaseMoshi.retrofitService.getOneGenreFromFirebaseRepo().await()
            database.trackDatabaseDao.insertAll(*playlist.asDatabaseModel())
        }
    }

    suspend fun getTracks() {
        withContext(Dispatchers.IO) {
            val playlist = BrowseFirebaseGson.retrofitService.getAllTracksDeferred()
            database.trackDatabaseDao.insertAll(*playlist.asDatabaseModel())
        }
    }


}