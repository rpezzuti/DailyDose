package rhett.pezzuti.dailydose.data

import com.squareup.moshi.Json

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