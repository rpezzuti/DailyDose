package rhett.pezzuti.dailydose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import rhett.pezzuti.dailydose.data.domain.Track


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
    val timestamp: Long,

    @ColumnInfo(name = "is_favorite")
    var favorite: Boolean
)

fun List<DatabaseTrack>.asDomainModel(): List<Track> {
    return map {
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

data class User (
    val username: String,
    val dubstep : Boolean = false,
    val melodicDubstep: Boolean = false,
    val loFi: Boolean = false,
    val chillstep: Boolean = false,
    val futureGarage: Boolean = false,
    val pianoAmbient: Boolean = false,
    val experimentalBass: Boolean = false,
    val liquidDnB: Boolean = false,
    val ambientBass: Boolean = false,
    val metalcore: Boolean = false,
    val acousticBallads: Boolean = false,
    val instrumentalRock: Boolean = false,
    val deathMetal: Boolean = false,
    val livePerformances: Boolean = false

)