package rhett.pezzuti.dailydose.network

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.getDatabase
import rhett.pezzuti.dailydose.utils.sendNotification
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
import timber.log.Timber

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // This chunk is triggered when the remoteMessage is received when the app is in the foreground. any fragment
        // When the app is in the background, the device receives the notification as displayed is the Firebase Console

        // Notification.Body = text
        // Data is stored as key value pairs

        val newTrack = createTrackFromMessage(remoteMessage)
        sendNotificationWithIntent(newTrack)

/*        // Save genre
        sendNotificationWithIntent(
            remoteMessage.data["title"]!!,
            remoteMessage.data["artist"]!!,
            remoteMessage.data["url"]!!
        )*/

        saveTrackInformation(remoteMessage)


        /**
        remoteMessage?.data?.let {

        Timber.i( "Remote message data payload: ${remoteMessage.data}")
        Timber.i( "Remote message notification: ${remoteMessage.notification}")
        Timber.i( "Remote message notification body: ${remoteMessage.notification?.body}")

        remoteMessage.data.get("URL")?.let { it1 -> sendNotification(it1) }
        }
         */
        /**
        remoteMessage?.notification?.let {

        Log.d(TAG, "Message notification body: ${it.body}")
        sendNotification(it.body!!)
        }
         */
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

    private fun saveTrackInformation(remoteMessage: RemoteMessage) {




    }

    private fun createTrackFromMessage(remoteMessage: RemoteMessage): Track {

        // Create some kind of working Null Check.
        remoteMessage.data.let {
            val track = Track(
                remoteMessage.data["title"]!!,
                remoteMessage.data["artist"]!!,
                remoteMessage.data["url"]!!,
                remoteMessage.data["genre"]!!,
                remoteMessage.data["image"]!!,
            )

            return track
        }
    }


    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(messageBody, applicationContext)
    }

    private fun sendNotificationWithIntent(track: Track) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotificationWithIntent(track.title, track.artist, track.url, applicationContext)
    }

}