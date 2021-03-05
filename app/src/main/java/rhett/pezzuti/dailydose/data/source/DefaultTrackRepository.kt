package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineDispatcher
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.asDomainModel
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.local.TrackDatabase
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource

/** Concrete Implementation of loading tracks from the data sources into the offline cache. **/
class DefaultTrackRepository(private val database: TrackDatabase) : TrackRepository {

    // private val tracksLocalDataSource: TrackLocalDataSource = TrackLocalDataSource()

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

        // TODO this is done.
        // val remoteData = trackRemoteDataSource.getTracks()
        val remoteData = listOf<Track>()

        if (remoteData.isNullOrEmpty()) {
            // TODO maybe throw an error?
            // do nothing
        } else {
            // TODO this is the next step
            // I want to update the fetched tracks with the favorites
            // Or find a way to only update the new ones, potentially.
            // Once a day, do the update. But should also have a force update version
            // Update the fetched tracks to identify data, then put them in the local

                // TODO sync somehow
            // tracksLocalDataSource.syncTracks()
            remoteData.forEach { track ->
                // TODO this works, but since i changed the signature, it flags
                // tracksLocalDataSource.addTrack(track)
            }
        }
    }


    override suspend fun getAllTracks(): LiveData<List<Track>> {
        return Transformations.map(database.trackDatabaseDao.getAllTracks()){
            it.asDomainModel()
        }
    }

    override suspend fun getTrackByGenre(genre: String): List<Track> {
        TODO("Not yet implemented")
    }


    override suspend fun favoriteTrack(timestamp: Long) {
        // TODO THis tuff
        // trackLocalDataSource.favorite(timestamp)
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        // TODO this should work
        //trackLocalDataSource.unFavorite(timestamp)
    }

    override suspend fun getFavorites(): List<Track> {
        // trackLocalDataSource.getFavorites()
        return emptyList()
    }

    override fun observeFavorites(): LiveData<List<Track>> {
        TODO("stuff")
        // trackLocalDataSource.observeFavorites()
    }

    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
    }

    override fun observeRecent(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    /** The other stuff i used previously. Pre DataSource stuff  **/
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