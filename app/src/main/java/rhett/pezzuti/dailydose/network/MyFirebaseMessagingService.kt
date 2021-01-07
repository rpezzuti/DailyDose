package rhett.pezzuti.dailydose.network

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import rhett.pezzuti.dailydose.utils.sendNotification

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // This chunk is triggered when the remoteMessage is received when the app is in the foreground.
        // When the app is in the background, the device receives the notification as normal and wanted.

        remoteMessage?.data?.let {
            Log.d(TAG, "Remote message data payload: " + remoteMessage.data)
        }


        remoteMessage?.notification?.let {
            Log.d(TAG, "Message notification body: ${it.body}")
            sendNotification(it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.i(TAG,"Here is your token: $token")

        sendRegistrationToServer(token)
    }

    /**
     * Persist token to third-party (your app) servers.
     */
    private fun sendRegistrationToServer(token: String?) {
        // Send the token to your server
    }


    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }


}