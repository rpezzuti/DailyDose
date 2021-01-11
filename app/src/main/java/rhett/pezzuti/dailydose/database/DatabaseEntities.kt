package rhett.pezzuti.dailydose.database

import android.provider.MediaStore
import androidx.room.*

/*
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
*/
