package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    /** Starts data request from Firebase. RemoteDataSource Only **/
    suspend fun refreshTracks(): List<Track>

    /** Get All / Add All **/
    fun observeAllTracks(): LiveData<List<Track>>
    suspend fun getAllTracks(): List<Track>



    /** Recent Tracks **/
    fun observeRecent(): LiveData<List<Track>>
    suspend fun getRecent(): List<Track>



    /** Favorite / Manipulation **/
    fun observeFavorites(): LiveData<List<Track>>
    suspend fun getFavorites(): List<Track>


    /** Genre **/
    fun observeGenre(genre: String): LiveData<List<Track>>
    suspend fun getGenre(genre: String): List<Track>


    /** Single Track Manipulation / Add All **/
    fun observeTrack(trackKey: Long): LiveData<Track>
    suspend fun getTrack(trackKey: Long): Track

    suspend fun favorite(trackId: Long)
    suspend fun unFavorite(trackId: Long)

    suspend fun updateTrack(track: Track)

    suspend fun addTrack(track: Track)
    suspend fun addAllTracks(tracks: List<Track>)



    /** Delete / Sync **/
    suspend fun deleteAllTracks()
    suspend fun syncTracks(remoteData: List<Track>)
}