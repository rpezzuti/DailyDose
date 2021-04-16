package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.Track
class FakeDataSource(var tracks: MutableList<Track> = mutableListOf()) : TrackDataSource {

    private val linkedTracks = LinkedHashMap<Long, Track>()

    override suspend fun getAllTracks(): List<Track> {
        return tracks.toList()
    }

    fun saveTrack(track: Track) {
        tracks.add(track)
    }

    override fun observeGenre(genre: String): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenre(genre: String): List<Track> {
        TODO("Not yet implemented")
    }

    override fun observeAllTracks(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun addAllTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun addTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrack(timestamp: Long): Track {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteTrack(timestamp: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        TODO("Not yet implemented")
    }

    override fun observeRecent(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAllTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSelected(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override fun observeFavorites(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTracks(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTotal() {
        tracks.clear()
    }

    override suspend fun syncTracks(remoteData: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<Track> {
        val favorites = mutableListOf<Track>()

        tracks.forEach { track ->
            if (track.favorite) {
                favorites.add(track)
            }
        }

        return favorites
    }

    override fun observeTrack(timestamp: Long): LiveData<Track> {
        TODO("Not yet implemented")
    }
}