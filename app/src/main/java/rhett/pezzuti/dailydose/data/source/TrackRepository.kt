package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track

// Methods to interact with the data.
// The database is the single source of truth!
interface TrackRepository {

    fun getAllTracks(): LiveData<List<Track>>

    /**
     *  When implemented concretely, calls updateTracksFromRemoteDataSource()
     *  Main access point for fetching data from network.
     */
    suspend fun refreshTracks()

    // This would be next, to actually give the data to the view model? i believe
    //fun observeTracks(): LiveData<List<Track>>

    // Should have a buncho functions here that actually do stuff
    suspend fun getTrackByGenre(genre: String): List<Track>


    /**
     * Favorite Track Manipulation
     */
    suspend fun favoriteTrack(timestamp: Long)
    suspend fun unFavoriteTrack(timestamp: Long)



    /**
     *  Paired methods for getting favorites and recent
     *  Only LD version in use so far
     */
    suspend fun getFavorites(): List<Track>
    fun observeFavorites(): LiveData<List<Track>>

    suspend fun getRecent(): List<Track>
    fun observeRecent(): LiveData<List<Track>>




    /**
     *  Only ones I really used. Get -> Update pattern for favorite
     */
    suspend fun getTrack(timestamp: Long): Track
    suspend fun updateTrack(track: Track)




    /** Generic Add **/
    suspend fun addTrack(track: Track)

}