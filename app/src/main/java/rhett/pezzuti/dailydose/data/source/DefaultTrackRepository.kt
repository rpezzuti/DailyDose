package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.asDomainModel
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.local.TrackDatabase

/** Concrete Implementation of loading tracks from the data sources into the offline cache. **/
class DefaultTrackRepository(private val database: TrackDatabase) : TrackRepository {

    override suspend fun refreshTracks() {
        /** One of the main methods **/
        // This function starts the process of refreshing the data from the network,
        // storing it in the remote data source,
        // then putting it in the local data source.
        updateTracksFromRemoteDataSource()
    }

    private suspend fun updateTracksFromRemoteDataSource() {
        /** Another main method **/
        // This is where we get the data from the network
        // Store it in the remote data source
        // Then use the remote data source to store it in the local data source

        val remoteData = tracksRemoteDataSource.getTracks()
    }


    override suspend fun getAllTracks(): LiveData<List<Track>> {
        return Transformations.map(database.trackDatabaseDao.getAllTracks()){
            it.asDomainModel()
        }
    }

    override suspend fun getTrackByGenre(genre: String): List<Track> {
        TODO("Not yet implemented")
    }

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

    var favorites = listOf<DatabaseTrack>()
}