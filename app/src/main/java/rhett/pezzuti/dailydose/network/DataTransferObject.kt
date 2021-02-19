package rhett.pezzuti.dailydose.network


import com.squareup.moshi.JsonClass
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.domain.Track

@JsonClass(generateAdapter = true)
data class NetworkTrackContainer (val tracks: List<NetworkTrack>)

@JsonClass(generateAdapter = true)
data class NetworkTrack(
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long,
    val favorite: Boolean
)

fun NetworkTrackContainer.asDomainModel(): List<Track> {
    return tracks.map {
        Track (
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            timestamp = it.timestamp,
            favorite = it.favorite
        )
    }
}

fun NetworkTrackContainer.asDatabaseModel(): Array<DatabaseTrack> {
    return tracks.map {
        DatabaseTrack (
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

data class FirebaseTrack(
    val title: String,
    val track: Track
)
