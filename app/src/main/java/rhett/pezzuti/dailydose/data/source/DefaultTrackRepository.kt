package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.asDomainModel
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.local.TrackDatabase
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource

/** Concrete Implementation of loading tracks from the data sources into the offline cache. **/
class DefaultTrackRepository(
    private val trackLocalDataSource: TrackDataSource,
    private val trackRemoteDataSource: TrackDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackRepository {

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

        // TODO this is done. ALTHOUGH I NEED TO GET THE TASKS FROM REMOTE
        // The secret was awaiting the response, and playing with the response directly. :)
        // prolly not ideal but it works
        val remoteData = trackRemoteDataSource.refreshTracks()

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
            trackLocalDataSource.syncTracks(remoteData)
        }
    }


    override fun getAllTracks(): LiveData<List<Track>> {
       return trackLocalDataSource.getTracks()
    }

    override suspend fun getTrackByGenre(genre: String): List<Track> {
        TODO("Not yet implemented")
    }


    override suspend fun favoriteTrack(timestamp: Long) {
        trackLocalDataSource.favorite(timestamp)
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        trackLocalDataSource.unFavorite(timestamp)
    }

    override suspend fun getFavorites(): List<Track> {
        // trackLocalDataSource.getFavorites()
        return emptyList()
    }

    override fun observeFavorites(): LiveData<List<Track>> {
        return trackLocalDataSource.observeFavorites()
    }

    /** Recent Tracks **/
    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
    }
    override fun observeRecent(): LiveData<List<Track>> {
        return trackLocalDataSource.observeRecent()
    }

    /** Single Tracks **/
    override suspend fun addTrack(track: Track) {
        track.apply {
            trackLocalDataSource.addTrack(this)
        }
    }

    override suspend fun getTrack(timestamp: Long): Track {
        return trackLocalDataSource.getTrack(timestamp)
    }

    override suspend fun updateTrack(track: Track) {
        trackLocalDataSource.updateTrack(track)
    }

    /** The other stuff i used previously. Pre DataSource stuff  **/
    // Public access of all the tracks in the database.
    // Fetching all the DATABASE tracks and returning them as DOMAIN tracks
/*    val tracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getAllTracks())
        {
            it.asDomainModel()
        }

    val recentTracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getRecentTracks())
        {
            it.asDomainModel()
        }*/

    var favorites = listOf<DatabaseTrack>()
}