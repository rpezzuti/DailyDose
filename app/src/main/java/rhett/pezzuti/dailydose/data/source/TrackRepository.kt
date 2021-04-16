package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track

// Methods to interact with the data.
// The database is the single source of truth!
interface TrackRepository {

    // TODO Fully get this thing working. Add functions that filter

    /**
     *  When implemented concretely, calls updateTracksFromRemoteDataSource()
     *  Main access point for fetching data from network.
     */
    suspend fun refreshTracks()

    /** All Tracks **/
    suspend fun getAllTracks(): List<Track>
    fun observeAllTracks(): LiveData<List<Track>>

    /**
     *  Paired methods for getting favorites and recent
     *  Only LD version in use so far
     */
    suspend fun getFavorites(): List<Track>
    fun observeFavorites(): LiveData<List<Track>>

    suspend fun getRecent(): List<Track>
    fun observeRecent(): LiveData<List<Track>>

    
    fun observeGenre(genre: String): LiveData<List<Track>>
    suspend fun getGenre(genre: String): List<Track>


    /**
     * Favorite Track Manipulation
     */
    suspend fun favoriteTrack(timestamp: Long)
    suspend fun unFavoriteTrack(timestamp: Long)






    /**
     *  Only ones I really used. Get -> Update pattern for favorite
     */
    suspend fun getTrack(timestamp: Long): Track
    suspend fun updateTrack(track: Track)




    /** Generic Add **/
    suspend fun addTrack(track: Track)
    suspend fun addAllTracks(tracks: List<Track>)

    suspend fun syncTracks(tracks: List<Track>)
}