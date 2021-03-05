package rhett.pezzuti.dailydose.data.source.remote

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.network.BrowseFirebaseMoshi
import rhett.pezzuti.dailydose.utils.asListOfTracks
import timber.log.Timber
import java.util.*

object TrackRemoteDataSource : TrackDataSource {

    override suspend fun refreshTracks(): List<Track> {

        // var data = listOf<Track>()
        return BrowseFirebaseGson.retrofitService.getAllTracks().awaitResponse().body().asListOfTracks()

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

    override fun getTracks(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTracks(tracks: List<Track>) {
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
        TODO("Not yet implemented")
    }

    override suspend fun syncTracks(remoteData: List<Track>) {
        TODO("Not yet implemented")
    }
}