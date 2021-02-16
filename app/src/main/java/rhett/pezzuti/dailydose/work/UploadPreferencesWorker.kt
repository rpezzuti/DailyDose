package rhett.pezzuti.dailydose.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.User
import java.net.HttpRetryException

class UploadPreferencesWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "UploadPreferencesWorker"
    }



    override suspend fun doWork(): Result {
        val sharedPref = applicationContext.getSharedPreferences(applicationContext.getString(R.string.user_preferences_key), Context.MODE_PRIVATE)
        return try {
            // Upload only if they have input their name.
            if (sharedPref.getString("username", "dummyName") != "dummyName") {

                val user = User(
                    sharedPref.getString("username", "dummyName")!!,
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_DUBSTEP), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_MELODIC_DUBSTEP), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_LO_FI), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_CHILLSTEP), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_FUTURE_GARAGE), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_PIANO_AMBIENT), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_EXPERIMENTAL_BASS), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_LIQUID_DNB), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_AMBIENT_BASS), false),

                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_METALCORE), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_ACOUSTIC_BALLADS), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_INSTRUMENTAL_ROCK), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_DEATH_METAL), false),
                    sharedPref.getBoolean(applicationContext.getString(R.string.TOPIC_LIVE_PERFORMANCES), false),
                )

                FirebaseDatabase.getInstance().reference.child("users").child(user.username).setValue(user)
            }
            Result.retry()
        } catch (e: HttpRetryException) {
            Result.retry()
        }
    }
}