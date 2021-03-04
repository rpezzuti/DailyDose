package rhett.pezzuti.dailydose.data.source.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks
import timber.log.Timber

class TrackRemoteDataSource : TrackDataSource {

    override suspend fun getTracks(): List<Track> {
        // This is where all the retrofit code would go.

        BrowseFirebaseGson.retrofitService.getAllTracks().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Timber.i("GREAT SUCCESS: ${response.body().toString()}")
                response.body().asListOfTracks()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Timber.i("Failure: " + t.message)
            }
        })


        // TODO add the retrofit code here my bro.
        return emptyList()
    }
}