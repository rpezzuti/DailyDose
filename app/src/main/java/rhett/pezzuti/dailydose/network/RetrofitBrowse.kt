package rhett.pezzuti.dailydose.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val FIREBASE_BASE_URL = "https://daily-dose-f1709-default-rtdb.firebaseio.com/"

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
}

object BrowseFirebaseGson {
    val retrofitService : BrowseFirebaseApiService by lazy {
        gsonRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}



