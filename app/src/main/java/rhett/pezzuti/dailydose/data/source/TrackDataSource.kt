package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    // Remote Only
    /**
     * Starts data request from Firebase. RemoteDataSource Only
     */
    suspend fun refreshTracks(): List<Track>


    /**
     * Inserts remote data into database, and updates it based on the favorites from the local data source.
     */
    suspend fun syncTracks(remoteData: List<Track>)


    /**
     *
     */
    suspend fun favoriteTrack(timestamp: Long)
    suspend fun unFavoriteTrack(timestamp: Long)


    /**
     *
     */
    fun observeFavorites(): LiveData<List<Track>>
    suspend fun getFavorites(): List<Track>


    /**
     *
     */
    fun observeRecent(): LiveData<List<Track>>
    suspend fun getRecent(): List<Track>


    /**
     *
     */
    fun observeGenre(genre: String): LiveData<List<Track>>
    suspend fun getGenre(genre: String): List<Track>










    /**
     *
     */
    suspend fun addTrack(track: Track)
    suspend fun addAllTracks(tracks: List<Track>)


    /**
     *
     */
    suspend fun getTrack(timestamp: Long): Track
    suspend fun getAllTracks(): List<Track>


    /**
     *
     */
    fun observeTrack(timestamp: Long): LiveData<Track>
    fun observeAllTracks(): LiveData<List<Track>>


    /**
     *
     */
    suspend fun updateTrack(track: Track)
    suspend fun updateAllTracks(tracks: List<Track>)


    /**
     *
     */
    suspend fun deleteTrack(track: Track)
    suspend fun deleteAllSelected(tracks: List<Track>)
    suspend fun deleteAllTotal()
}