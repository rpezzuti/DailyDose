package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track

// Methods to interact with the data.
// The database is the single source of truth!
interface TrackRepository {

    /**
     *  Main access point for fetching data from network.
     *  Calls updateTracksFromRemoteDataSource(), forcing a refresh from Firebase.
     */
    suspend fun refreshTracks()

    /**
     *
     */
    suspend fun syncTracks(tracks: List<Track>)

    /**
     * Favorite Track Manipulation
     */
    suspend fun favoriteTrack(timestamp: Long)
    suspend fun unFavoriteTrack(timestamp: Long)

    /**
     *  Paired methods for getting favorites and recent
     *  Only LD version in use so far
     */
    fun observeFavorites(): LiveData<List<Track>>
    suspend fun getFavorites(): List<Track>

    /**
     *
     */
    fun observeRecent(): LiveData<List<Track>>
    suspend fun getRecent(): List<Track>


    fun observeGenre(genre: String): LiveData<List<Track>>
    suspend fun getGenre(genre: String): List<Track>









    /**
     * Adds a single track to local data source, which adds the track to database.
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