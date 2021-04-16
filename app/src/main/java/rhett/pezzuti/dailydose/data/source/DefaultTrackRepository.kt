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
     * Main access method to start request from Firebase Database. Calls updateTracksFromRemoteDataSource()
     */
    override suspend fun refreshTracks() {
        updateTracksFromRemoteDataSource()
    }

    /**
     * Forces refresh from remote data source. If refresh is successful and contains data, syncs data into database.
     */
    private suspend fun updateTracksFromRemoteDataSource() {

        val remoteData = trackRemoteDataSource.refreshTracks()

        if (remoteData.isNullOrEmpty()) {
            // Break & throw error
        } else {
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

    override suspend fun addAllTracks(tracks: List<Track>) {
        trackLocalDataSource.addAllTracks(tracks)
    }

    override suspend fun syncTracks(tracks: List<Track>) {
        trackLocalDataSource.syncTracks(tracks)
    }
}