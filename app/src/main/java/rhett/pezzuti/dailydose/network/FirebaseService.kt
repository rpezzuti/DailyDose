package rhett.pezzuti.dailydose.network

import android.media.MediaParser
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack

private const val BASE_URL = "enter_firebase_url_here"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface FirebaseService {

    @GET("trackdata")
    fun getTracks(@Query("filter") type: String):
            Deferred<List<DatabaseTrack>>
}

object FirebaseApi {
    val retrofitService : FirebaseService by lazy {
        retrofit.create(FirebaseService::class.java)
    }
}
