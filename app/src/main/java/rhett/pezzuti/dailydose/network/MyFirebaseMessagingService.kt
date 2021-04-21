package rhett.pezzuti.dailydose.network

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.source.local.getInstance
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
import java.lang.Exception

class MyFirebaseMessagingService(
    private val trackDatabaseDao: TrackDatabaseDao
): FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // A remote message with a payload triggers onMessageReceived, instead of being handled by the System Tray.
        if (remoteMessage.data.isNotEmpty()) {
            remoteMessage.data.let {
                val track = createTrackFromMessage(remoteMessage)

                /** Show Notification **/
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
                val showNotification = sharedPref.getBoolean("notifications_switch", true)

                if (showNotification) {
                    sendNotificationWithIntent(track)
                }

                /** Save to Local Database **/
                saveTrackToDatabase(track)
            }
        }

    }

    /**
     * Save user's token to firebase.
     */
    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }


    /**
     * Persist token to third-party (your app) servers.
     */
    private fun sendRegistrationToServer(token: String?) {
        val firebaseDatabase = Firebase.database.reference

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        val userName = sharedPref.getString(getString(R.string.sharedpref_username_key), "username")

        firebaseDatabase.child("Tokens").child(userName!!).setValue(token)
    }

    private fun createTrackFromMessage(remoteMessage: RemoteMessage): Track {

        val timestamp = remoteMessage.data["timestamp"]!!.toLong()

        // Create some kind of working Null Check that doesn't crash the app
        remoteMessage.data.let {
            return Track(
                timestamp,
                remoteMessage.data["url"]!!,
                remoteMessage.data["title"]!!,
                remoteMessage.data["artist"]!!,
                remoteMessage.data["genre"]!!,
                remoteMessage.data["image"]!!,
                false
            )
        }
    }

    private fun saveTrackToDatabase(track: Track) {

        val databaseTrack = DatabaseTrack(
            track.timestamp,
            track.url,
            track.title,
            track.artist,
            track.genre,
            track.image,
            track.favorite
        )

        trackDatabaseDao.addTrack(databaseTrack)
    }

    private fun sendNotificationWithIntent(track: Track) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotificationWithIntent(track, applicationContext)
    }

}