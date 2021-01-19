package rhett.pezzuti.dailydose.database.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class DatabaseTrack (

    @PrimaryKey
    val url: String,

    @ColumnInfo(name = "track_title")
    val title: String,

    @ColumnInfo(name = "track_artist")
    val artist: String,

    @ColumnInfo(name = "track_genre")
    val genre: String,

    @ColumnInfo(name = "track_image")
    val image: String,

    @ColumnInfo(name = "track_timestamp")
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