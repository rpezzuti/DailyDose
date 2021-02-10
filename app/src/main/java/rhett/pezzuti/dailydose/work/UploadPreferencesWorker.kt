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
        val user = User(
            "dummyName",
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            )

        return try {
            FirebaseDatabase.getInstance().reference.child("users").child(user.username).setValue(user)
            Result.retry()
        } catch (e: HttpRetryException) {
            Result.retry()
        }
    }
}