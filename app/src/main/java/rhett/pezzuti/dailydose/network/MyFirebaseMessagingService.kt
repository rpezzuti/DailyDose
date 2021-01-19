package rhett.pezzuti.dailydose.network

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.utils.sendNotification
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
import timber.log.Timber

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Can't use context outside of a function in this class

        /**
         *                              TODO
         * STILL NEED TO HANDLE ALL THIS WHEN THE APP IS NOT IN THE FOREGROUND **/

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
                val databaseTrack = createDatabaseTrackFromMessage(remoteMessage)

                /** Save to Database **/
                saveTrackToDatabase(databaseTrack)

                /** Send back to Firebase Database **/
                saveTrackToFirebase(databaseTrack)

                /** Show Notification **/
                sendNotificationWithIntent(databaseTrack)
            }
        }

    }

    override fun onNewToken(token: String) {
        Timber.i("Here is your token: $token")

        sendRegistrationToServer(token)
    }

    /**
     * Persist token to third-party (your app) servers.
     */
    private fun sendRegistrationToServer(token: String?) {
        val firebaseDatabase = Firebase.database.reference

        firebaseDatabase.child("My Tokens").setValue(token)
    }

    private fun createDatabaseTrackFromMessage(remoteMessage: RemoteMessage): DatabaseTrack {

        // Create some kind of working Null Check.
        remoteMessage.data.let {
            return DatabaseTrack(
                remoteMessage.data["url"]!!,
                remoteMessage.data["title"]!!,
                remoteMessage.data["artist"]!!,
                remoteMessage.data["genre"]!!,
                remoteMessage.data["image"]!!,
            )
        }
    }

    private fun saveTrackToDatabase(databaseTrack: DatabaseTrack) {
        val database = getInstance(applicationContext).trackDatabaseDao
        database.insert(databaseTrack)
    }

    private fun saveTrackToFirebase(databaseTrack: DatabaseTrack) {

        val firebaseDatabase = Firebase.database.reference
        firebaseDatabase.child(databaseTrack.genre).child(databaseTrack.title).setValue(databaseTrack)

    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(messageBody, applicationContext)
    }

    private fun sendNotificationWithIntent(databaseTrack: DatabaseTrack) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotificationWithIntent(databaseTrack.title, databaseTrack.artist, databaseTrack.url, applicationContext)
    }

}