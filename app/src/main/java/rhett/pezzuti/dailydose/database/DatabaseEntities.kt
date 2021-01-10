package rhett.pezzuti.dailydose.database

import android.provider.MediaStore
import androidx.room.*
import rhett.pezzuti.dailydose.database.domain.Track

@Entity(tableName = "track_table")
data class DatabaseTrack (

    @PrimaryKey
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String
)

// Extension function to turn a DatabaseTrack object into a Track Domain object.
fun List<DatabaseTrack>.asDomainModel(): List<Track> {
    return map {
        Track (
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image)
    }
}
