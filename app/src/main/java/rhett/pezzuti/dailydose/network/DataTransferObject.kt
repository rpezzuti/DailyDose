package rhett.pezzuti.dailydose.network

import androidx.room.Database
import com.squareup.moshi.JsonClass
import rhett.pezzuti.dailydose.database.DatabaseTrack

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

fun NetworkTrackContainer.asDatabaseModel(): Array<DatabaseTrack> {
    return tracks.map {
        DatabaseTrack (
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            timestamp = it.timestamp
                )
    }.toTypedArray()
}
