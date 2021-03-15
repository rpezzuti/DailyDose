package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource

/** Concrete Implementation of loading tracks from the data sources into the offline cache. **/
class DefaultTrackRepository(
    private val trackLocalDataSource: TrackDataSource,
    private val trackRemoteDataSource: TrackDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackRepository {


     /**
     * Access point for loading paging data from firebase.
     */
    override fun getPagingResults(): Flow<PagingData<Track>> {
        return Pager(
            config = PagingConfig(
                pageSize = 44,
                enablePlaceholders = false
            ), pagingSourceFactory = {TrackRemoteDataSource}
        ).flow
    }

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


    override suspend fun getAllTracks(): List<Track> {
        val update = true
        if (update) {
            updateTracksFromRemoteDataSource()
        }
        return trackLocalDataSource.getAllTracks()
    }


    override fun observeAllTracks(): LiveData<List<Track>> {
       return trackLocalDataSource.observeAllTracks()
    }


    /**
     * Returns LiveData<List<Track>> from LocalDataSource
     */
    override fun observeGenre(genre: String): LiveData<List<Track>> {
        return trackLocalDataSource.observeGenre(genre)
    }


    /**
     * Returns List<Track> from LocalDataSource
     */
    override suspend fun getGenre(genre: String): List<Track> {
        return trackLocalDataSource.getGenre(genre)
    }


    /** Favorites **/
    override suspend fun favoriteTrack(timestamp: Long) {
        trackLocalDataSource.favorite(timestamp)
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        trackLocalDataSource.unFavorite(timestamp)
    }

    override suspend fun getFavorites(): List<Track> {
        return trackLocalDataSource.getFavorites()
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