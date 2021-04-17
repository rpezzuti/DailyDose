package rhett.pezzuti.dailydose.data.source.remote

import androidx.lifecycle.LiveData
import retrofit2.awaitResponse
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks
import java.lang.Exception
import kotlin.collections.LinkedHashMap

object TrackRemoteDataSource : TrackDataSource {

    // TODO want to have an actual piece of data here in the source.

    private var TRACK_REMOTE_DATA = LinkedHashMap<Long, Track>()

    override suspend fun refreshTracks(): List<Track> {

        try {
            val data = BrowseFirebaseGson.retrofitService.getAllTracks().awaitResponse().body().asListOfTracks()

            // Cache the remote data.
            data.forEach { track ->
                TRACK_REMOTE_DATA[track.timestamp] = track
            }

            // Returns the remote data, not the cache
            return data
        } catch (e: Exception) {

            // Return empty list upon error.
                // Could return the cache instead?
            return listOf()
        }

    }





    override suspend fun syncTracks(remoteData: List<Track>) {
        TODO("Not yet implemented")
    }













    override suspend fun addAllTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override fun observeGenre(genre: String): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenre(genre: String): List<Track> {
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


    override fun observeFavorites(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecent(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<Track> {
        TODO("Not yet implemented")
    }




    /**
     *
     */
    override fun observeTrack(timestamp: Long): LiveData<Track> {
        TODO("Not yet implemented")
    }

    override fun observeAllTracks(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }


    /**
     *
     */
    override suspend fun updateTrack(track: Track) {
        TODO("Not yet implemented")
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
        TRACK_REMOTE_DATA.clear()
    }
}