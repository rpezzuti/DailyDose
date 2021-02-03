package rhett.pezzuti.dailydose.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import rhett.pezzuti.dailydose.database.domain.LocalTrack

private const val FIREBASE_BASE_URL = "https://daily-dose-f1709-default-rtdb.firebaseio.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val firebaseRetrofit = Retrofit.Builder()
    .baseUrl(FIREBASE_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface BrowseFirebaseApiService {
    @GET(".json")
    fun getAllTracks():
            Call<List<LocalTrack>>

    @GET(".json")
    fun getJson():
            Call<JSONObject>

    @GET (".json")
    fun refreshDatbase(): Deferred<NetworkTrackContainer>
}

object BrowseFirebaseApi {
    val retrofitService : BrowseFirebaseApiService by lazy {
        firebaseRetrofit.create(BrowseFirebaseApiService::class.java)
    }
}

