package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track

/** Concrete Implementation of loading tracks from the data sources into the offline cache. **/
class DefaultTrackRepository(
    private val trackLocalDataSource: TrackDataSource,
    private val trackRemoteDataSource: TrackDataSource,
) : TrackRepository {

    /**
     * Main access method to start request from Firebase Database. Calls updateTracksFromRemoteDataSource()
     */
    override suspend fun refreshTracks() {
        updateTracksFromRemoteDataSource()
    }


    /**
     * Forces refresh from remote data source. Then syncs the remote data into the local data.
     * If refresh is unsuccessful, returns empty list.
     */
    private suspend fun updateTracksFromRemoteDataSource() {

        val remoteData = trackRemoteDataSource.refreshTracks()

        if (remoteData.isNullOrEmpty()) {
            // Break & throw error
            // Or. If the cache is being returned, this would only be called as an endpoint case.
            // I.E if the user is browsing for the first time, and has nothing in the cache.
        } else {
            trackLocalDataSource.syncTracks(remoteData)
        }
    }


    /**
     *
     */
    override suspend fun syncTracks(tracks: List<Track>) {
        trackLocalDataSource.syncTracks(tracks)
    }


    /**
     *
     */
    override suspend fun favoriteTrack(timestamp: Long) {
        trackLocalDataSource.favoriteTrack(timestamp)
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        trackLocalDataSource.unFavoriteTrack(timestamp)
    }


    /**
     *
     */
    override fun observeFavorites(): LiveData<List<Track>> {
        return trackLocalDataSource.observeFavorites()
    }

    override suspend fun getFavorites(): List<Track> {
        return trackLocalDataSource.getFavorites()
    }


    /**
     *
     */
    override fun observeRecent(): LiveData<List<Track>> {
        return trackLocalDataSource.observeRecent()
    }

    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
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


    /**
     *
     */
    override suspend fun addTrack(track: Track) {
        track.apply {
            trackLocalDataSource.addTrack(this)
        }
    }

    override suspend fun addAllTracks(tracks: List<Track>) {
        trackLocalDataSource.addAllTracks(tracks)
    }


    /**
     *
     */
    override suspend fun getTrack(timestamp: Long): Track {
        return trackLocalDataSource.getTrack(timestamp)
    }

    override suspend fun getAllTracks(): List<Track> {
        val update = true
        if (update) {
            updateTracksFromRemoteDataSource()
        }
        return trackLocalDataSource.getAllTracks()
    }


    /**
     *
     */
    override fun observeTrack(timestamp: Long): LiveData<Track> {
        TODO("Not yet implemented")
    }

    override fun observeAllTracks(): LiveData<List<Track>> {
        return trackLocalDataSource.observeAllTracks()
    }


    /**
     *
     */
    override suspend fun updateTrack(track: Track) {
        trackLocalDataSource.updateTrack(track)
    }

    override suspend fun updateAllTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }


    /**
     *
     */
    override suspend fun deleteTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSelected(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTotal() {
        TODO("Not yet implemented")
    }

}