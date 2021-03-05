package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    fun getTracks(): LiveData<List<Track>>
    suspend fun addTracks(tracks: List<Track>)


    suspend fun addTrack(track: Track)
    suspend fun getTrack(trackKey: Long): Track

    suspend fun favorite(trackId: Long)
    suspend fun unFavorite(trackId: Long)

    fun observeRecent(): LiveData<List<Track>>

    suspend fun updateTrack(track: Track)

    fun observeFavorites(): LiveData<List<Track>>

    suspend fun refreshTracks(): List<Track>

    suspend fun deleteAllTracks()
    suspend fun syncTracks(remoteData: List<Track>)
}