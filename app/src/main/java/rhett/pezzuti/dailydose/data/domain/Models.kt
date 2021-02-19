package rhett.pezzuti.dailydose.data.domain

import com.squareup.moshi.Json
import rhett.pezzuti.dailydose.data.DatabaseTrack

data class Track (
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long,
    var favorite: Boolean
)

fun List<Track>.asDatabaseModel(): Array<DatabaseTrack> {
    return map {
        DatabaseTrack(
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            timestamp = it.timestamp,
            favorite = it.favorite
        )
    }.toTypedArray()
}

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
    val timestamp: Long,

    @Json(name = "favorite")
    val favorite: Boolean
)

// For Retrofit to accept the notification data, there must be a variable
// annotated "data" for the payload to go through.
// The TO defines the TOPIC
data class TrackNotification(
    val data: Track,
    val to: String
)