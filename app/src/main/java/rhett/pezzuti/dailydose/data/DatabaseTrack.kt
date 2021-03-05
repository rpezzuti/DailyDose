package rhett.pezzuti.dailydose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "track_table")
data class DatabaseTrack (

    @PrimaryKey
    @ColumnInfo(name = "track_timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "track_url")
    val url: String,

    @ColumnInfo(name = "track_title")
    val title: String,

    @ColumnInfo(name = "track_artist")
    val artist: String,

    @ColumnInfo(name = "track_genre")
    val genre: String,

    @ColumnInfo(name = "track_image")
    val image: String,

    @ColumnInfo(name = "is_favorite")
    var favorite: Boolean
)

fun List<DatabaseTrack>.asDomainModel(): List<Track> {
    return map {
        Track (
            timestamp = it.timestamp,
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            favorite = it.favorite
                )
    }
}
