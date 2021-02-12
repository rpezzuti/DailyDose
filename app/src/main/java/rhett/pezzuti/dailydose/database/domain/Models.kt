package rhett.pezzuti.dailydose.database.domain

import com.squareup.moshi.Json

data class Track (
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long,
    var favorite: Boolean
)

data class LocalTrack (

    @Json(name = "url")
    val url: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "artist")
    val artist: String,

    @Json(name = "genre")
    val genre: String,

    @Json(name = "image")
    val image: String,

    @Json(name = "timestamp")
    val timestamp: Long
)

// For Retrofit to accept the notification data, there must be a variable
// annotated "data" for the payload to go through.
// The TO defines the TOPIC
data class TrackNotification(
    val data: Track,
    val to: String
)