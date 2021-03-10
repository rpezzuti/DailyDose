package rhett.pezzuti.dailydose.main.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository

class FakeTrackRepository : TrackRepository {
    override fun getAllTracks(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTracks() {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackByGenre(genre: String): List<Track> {
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

    override suspend fun addTrack(track: Track) {
        TODO("Not yet implemented")
    }
}