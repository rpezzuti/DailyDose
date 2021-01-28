package rhett.pezzuti.dailydose.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import rhett.pezzuti.dailydose.database.domain.TrackNotification

private const val BASE_URL = "https://fcm.googleapis.com"
private const val SERVER_KEY = "AAAAuHEb2rk:APA91bGkE64oKdq8upF2T-oBw5e2idoRhosUM3i6TvqWkFDsoDkN1_3pAlk4W0e6INfeNW3mlcdnOhww8NVUY7V71CW3E0rHaX54lkDv_c5iDtaKeQ_g1BCGfLoHIjQ-2pcqxvoqVFYy"
private const val CONTENT_TYPE = "application/json"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface FirebaseService {

    @GET("trackdata")
    fun getTracks(@Query("filter") type: String):
            Deferred<NetworkTrackContainer>

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    // Make suspend and put on IO?
    suspend fun postNotification(
        @Body notification: TrackNotification
    ): Response<ResponseBody>
}

object FirebaseApi {
    val retrofitService : FirebaseService by lazy {
        retrofit2.create(FirebaseService::class.java)
    }
}


interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: TrackNotification
    ): Response<ResponseBody>
}


class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(NotificationAPI::class.java)
        }
    }
}
