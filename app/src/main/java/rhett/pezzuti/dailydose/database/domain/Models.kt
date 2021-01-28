package rhett.pezzuti.dailydose.database.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import rhett.pezzuti.dailydose.database.DatabaseTrack

data class Track (
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class NotificationData (
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String
)

// For Retrofit to accept the notification data, there must be a variable annotated "data" for the payload to go through.
// The TO defines the TOPIC
data class TrackNotification(
    val data: NotificationData,
    val to: String
)