package rhett.pezzuti.dailydose.viewmodels

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.domain.TrackNotification
import rhett.pezzuti.dailydose.network.FirebaseApi
import rhett.pezzuti.dailydose.network.RetrofitInstance
import rhett.pezzuti.dailydose.receiver.AlarmReceiver
import rhett.pezzuti.dailydose.utils.sendNotification
import timber.log.Timber

class UploadViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _eventUploadCheck = MutableLiveData<Boolean>()
    val eventUploadCheck: LiveData<Boolean>
        get() = _eventUploadCheck


    init {
        _eventUploadCheck.value = false
    }


    fun uploadCheck() {
        _eventUploadCheck.value = true
    }

    fun doneUploadCheck() {
        _eventUploadCheck.value = false
    }


    fun subscribeTopic(genre: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(genre)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $genre"
                if (!task.isSuccessful) {
                    msg = app.resources.getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(app.applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    fun unSubscribeTopic(genre: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(genre)
            .addOnCompleteListener { task ->
                var msg = "Removed $genre from subscriptions"
                if (!task.isSuccessful) {
                    msg = app.resources.getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(app.applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
    }


    fun sendNotification(notification: TrackNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //val response = RetrofitInstance.api.postNotification(notification)
                val response = FirebaseApi.retrofitService.postNotification(notification)
                if (response.isSuccessful) {
//                    Timber.d( "Response: ${Gson().toJson(response)}")
                    Timber.d("Response Body: ${response.body().toString()}")
                    Timber.d("Response Is Successful: ${response.isSuccessful}")
                    Timber.d("Response Code: ${response.code()}")
                    Timber.d("Response Raw: ${response.raw()}")
                    Timber.d("Response Headers: ${response.headers()}")
                    Timber.d("Response Message: ${response.message()}")
                } else {
                    Timber.e("Error code: ${response.code()} ${response.errorBody().toString()}")
                    Timber.e("Failure Receiving response from RetrofitInstance")
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
            }
        }
    }

}
    /*fun sendNotification2(notification: TrackNotification) {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                //Timber.d( "Response: ${Gson().toJson(response)}")
                Timber.d("Response Body: ${response.body().toString()}")
                Timber.d("Response Is Successful: ${response.isSuccessful}")
                Timber.d("Response Code: ${response.code()}")
                Timber.d("Response Raw: ${response.raw()}")
                Timber.d("Response Headers: ${response.headers()}")
                Timber.d("Response Message: ${response.message().toString()}")
            } else {
                Timber.e("Error code: ${response.code()} ${response.errorBody().toString()}")
                Timber.e("Failure Receiving response from RetrofitInstance")
            }
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }*/





// Broken shit below




// Doesn't work. Prolly has to do with the context.
/*fun uploadCheck() {
    val builder = AlertDialog.Builder(app.applicationContext)
        .setTitle("UPLOAD?!")
        .setMessage("Are you sure you want to upload?")

    // When you click outside, it will not close.
    builder.setCancelable(false)

    builder.setPositiveButton(
        "Yes, Upload",
        DialogInterface.OnClickListener { dialog, id ->
            Toast.makeText(app, "Uplaoded", Toast.LENGTH_SHORT).show()
        }
    )

    builder.setNegativeButton(
        "No, wait",
        DialogInterface.OnClickListener { dialog, id ->
            Toast.makeText(app, "Didn't upload", Toast.LENGTH_SHORT).show()
        }
    )

    builder.create()

    builder.show()
}*/

/*
fun sendNotification() {

    val notificationManager = ContextCompat.getSystemService(
        app.applicationContext,
        NotificationManager::class.java
    ) as NotificationManager

    notificationManager.sendNotification(
        "text",
        app.applicationContext
    )
}
*/
