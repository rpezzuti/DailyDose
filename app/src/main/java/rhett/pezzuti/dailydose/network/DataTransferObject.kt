package rhett.pezzuti.dailydose.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkTrackContainer (val tracks: List<NetworkTrack>)

@JsonClass(generateAdapter = true)
data class NetworkTrack(
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long
)
