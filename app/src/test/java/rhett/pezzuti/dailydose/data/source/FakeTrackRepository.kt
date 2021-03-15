package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository

class FakeTrackRepository : TrackRepository {


    private var tracksData: LinkedHashMap<Long, Track> = LinkedHashMap()

    fun addTracks(vararg tracks: Track) {
        for (track in tracks) {
            tracksData[track.timestamp] = track
        }
    }

    fun cleanData() {
        tracksData.clear()
    }

    override suspend fun addTrack(track: Track) {
        tracksData[track.timestamp] = track
    }

    override fun observeGenre(genre: String): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenre(genre: String): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<Track> {
        TODO("Not yet implemented")
    }

    override fun observeAllTracks(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTracks() {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteTrack(timestamp: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<Track> {
        TODO("Not yet implemented")
    }

    override fun observeFavorites(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
    }

    override fun observeRecent(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrack(timestamp: Long): Track {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override fun getPagingResults(): Flow<PagingData<Track>> {
        TODO("Not yet implemented")
    }
}