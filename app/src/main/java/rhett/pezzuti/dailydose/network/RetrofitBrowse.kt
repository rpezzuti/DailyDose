package rhett.pezzuti.dailydose.network

import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val FIREBASE_BASE_URL = "https://daily-dose-f1709-default-rtdb.firebaseio.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val moshiRetrofit = Retrofit.Builder()
    .baseUrl(FIREBASE_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

private val gsonRetrofit = Retrofit.Builder()
    .baseUrl(FIREBASE_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface BrowseFirebaseApiService {

    // Gson, get all the babies.
    // The dream has been realized.
    /**
     * The dream has been realized. Gets a JSON object that has all of the tracks with genres as keys
     */
    @GET ("tracks.json")
    fun getAllTracks():
            Call<JsonObject>



    // Using Deferred<JsonObject> returns the same call adapter error.
    /**
     * Doesn't work. Attempt at using a DTO and deferred
     */
    @GET ("tracks.json")
    fun getAllTracksDeferred():
        Deferred<NetworkTrackContainerJson>

}

object BrowseFirebaseMoshi {
    val retrofitService : BrowseFirebaseApiService by lazy {
        moshiRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}

object BrowseFirebaseGson {
    val retrofitService : BrowseFirebaseApiService by lazy {
        gsonRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}



