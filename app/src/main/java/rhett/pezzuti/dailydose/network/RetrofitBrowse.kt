package rhett.pezzuti.dailydose.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import rhett.pezzuti.dailydose.database.domain.LocalTrack
import rhett.pezzuti.dailydose.database.domain.Track

private const val FIREBASE_BASE_URL = "https://daily-dose-f1709-default-rtdb.firebaseio.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val moshiRetrofit = Retrofit.Builder()
    .baseUrl(FIREBASE_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

// Different scalars factory, in an attempt to get a JSON object.
// Works when getting listOf(track1, track2)
private val scalarsRetrofit = Retrofit.Builder()
    .baseUrl(FIREBASE_BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

interface BrowseFirebaseApiService {

    /**
     * GET A JSON OBJECT
     * - Scalars RETROFIT
     * - @GET ("tracks/test-genre.json")
     * - :Call<String>
     */

    @GET(".json")
    fun getAllTracks():
            Call<List<LocalTrack>>


    //          SCALARS
    // @GET ("tracks/test-genre") returns HTML
    // @GET ("tracks.test-genre.json) returns A full JSON object of the entire Genre
    // @GET ("tracks/test-genre/It%20Sounds%20Like%20We're%20Breaking.json") returns just that track
    // If i try to return a Call<Track> instead of Call<String>, i get a converter Error
            // - for @GET ("tracks/test-genre/It%20Sounds%20Like%20We're%20Breaking.json")
    @GET ("tracks/test-genre.json")
    fun getOneJsonString():
            Call<String>

    @GET("tracks/dummy-genre/track.json")
    fun getOneTrackFromFirebase():
            Call<Track>

    @GET("tracks/test-genre-list.json")
    fun getTestGenreListTrack():
            Call<List<Track>>

    @GET("tracks/test-genre-list.json")
    fun getOneGenreFromFirebaseRepo():
            Deferred<List<Track>>

    @GET (".json")
    fun refreshDatabase(): Deferred<NetworkTrackContainer>
}

object BrowseFirebaseMoshi {
    val retrofitService : BrowseFirebaseApiService by lazy {
        moshiRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}

// In an attempt to get JSON data. Expects an array, but is an object.
object BrowseFirebaseScalars {
    val retrofitService : BrowseFirebaseApiService by lazy {
        scalarsRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}



