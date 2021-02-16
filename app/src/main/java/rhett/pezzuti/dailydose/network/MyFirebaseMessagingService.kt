package rhett.pezzuti.dailydose.network

import android.app.NotificationManager
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.database.User
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.utils.sendNotification
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
import timber.log.Timber
import java.lang.Exception

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Can't use context outside of a function in this class



        // This chunk is triggered when the remoteMessage is received when the app is in the foreground. any fragment
        // When the app is in the background, the device receives the notification as displayed is the Firebase Console

        // Notification.Body = text
        // Data is stored as key value pairs

        Timber.i("Has the message been received: $remoteMessage")

        // If said child does not exist, it is created.

        // Genre -> Track Title.
        // When users fetch a genre, they get all the tracks.

        // App State        Notification        Data                Both
        // Foreground       onMessageReceived   onMessageReceived   onMessageReceived
        // Background       System Tray         onMessageReceived   Notification: System tray. Data: in extras of the intent.

        // Data Payload.
        if (remoteMessage.data.isNotEmpty()) {
            remoteMessage.data.let {
                val track = createTrackFromMessage(remoteMessage)

                /** Show Notification **/
                sendNotificationWithIntent(track)

                /** Save to Database **/
                saveTrackToDatabase(track)
            }
        }

    }

    override fun onNewToken(token: String) {
        Timber.i("Here is your token: $token")

        sendRegistrationToServer(token)
    }

    override fun onMessageSent(messageId: String) {
        super.onMessageSent(messageId)

        Timber.i("Send Message $messageId")
    }
    override fun onSendError(messageId: String, e: Exception) {
        super.onSendError(messageId, e)

        Timber.i("Error: ${e.message}")
    }

    /**
     * Persist token to third-party (your app) servers.
     */
    private fun sendRegistrationToServer(token: String?) {
        val firebaseDatabase = Firebase.database.reference

        firebaseDatabase.child("My Tokens").setValue(token)
    }

    private fun createTrackFromMessage(remoteMessage: RemoteMessage): Track {

        val timestamp = remoteMessage.data["timestamp"]!!.toLong()

        // Create some kind of working Null Check that doesn't crash the app
        remoteMessage.data.let {
            return Track(
                remoteMessage.data["url"]!!,
                remoteMessage.data["title"]!!,
                remoteMessage.data["artist"]!!,
                remoteMessage.data["genre"]!!,
                remoteMessage.data["image"]!!,
                timestamp,
                false
            )
        }
    }

    private fun saveTrackToDatabase(track: Track) {
        val database = getInstance(applicationContext).trackDatabaseDao

        val databaseTrack = DatabaseTrack(
            track.url,
            track.title,
            track.artist,
            track.genre,
            track.image,
            track.timestamp,
            track.favorite
        )

        database.insert(databaseTrack)
    }

    private fun sendNotificationWithIntent(track: Track) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotificationWithIntent(track.title, track.artist, track.url, applicationContext)
    }

}