package rhett.pezzuti.dailydose.data.source.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks
import timber.log.Timber

class TrackRemoteDataSource() : TrackDataSource {

    override suspend fun getTracks(): List<Track> {

        var data = listOf<Track>()

        BrowseFirebaseGson.retrofitService.getAllTracks().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Timber.i("GREAT SUCCESS: ${response.body().toString()}")
                data = response.body().asListOfTracks()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Timber.i("Failure: " + t.message)
            }
        })

        return data
    }

    override suspend fun addTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun addTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrack(trackKey: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrack(trackId: Long) {
        TODO("Not yet implemented")
    }
}