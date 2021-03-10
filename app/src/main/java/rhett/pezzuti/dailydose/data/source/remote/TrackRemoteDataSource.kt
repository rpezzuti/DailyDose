package rhett.pezzuti.dailydose.data.source.remote

import androidx.lifecycle.LiveData
import retrofit2.awaitResponse
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks
import kotlin.collections.LinkedHashMap

object TrackRemoteDataSource : TrackDataSource {

    // TODO want to have an actual piece of data here in the source.

    private var TRACK_REMOTE_DATA = LinkedHashMap<Long, Track>()

    override suspend fun refreshTracks(): List<Track> {

        val data = BrowseFirebaseGson.retrofitService.getAllTracks().awaitResponse().body().asListOfTracks()

        data.forEach { track ->
            TRACK_REMOTE_DATA[track.timestamp] = track
        }
        return data

       /* try {
            BrowseFirebaseGson.retrofitService.getAllTracks().enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Timber.i("GREAT SUCCESS: ${response.body().toString()}")
                    data = response.body().asListOfTracks()
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Timber.i("Failure: " + t.message)
                }
            })
        } catch (e: Exception) {
            Timber.i(e)
        }*/
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

    override suspend fun getTrack(trackKey: Long): Track {
        TODO("Not yet implemented")
    }

    override suspend fun favorite(trackId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavorite(trackId: Long) {
        TODO("Not yet implemented")
    }

    override fun observeRecent(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override fun observeFavorites(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTracks() {
        TRACK_REMOTE_DATA.clear()
    }

    override suspend fun syncTracks(remoteData: List<Track>) {
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

    override fun observeTrack(trackKey: Long): LiveData<Track> {
        TODO("Not yet implemented")
    }
}