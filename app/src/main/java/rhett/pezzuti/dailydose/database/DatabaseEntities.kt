package rhett.pezzuti.dailydose.database

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import rhett.pezzuti.dailydose.database.domain.Track


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

@Entity (tableName = "user_preferences")
data class User (

    @PrimaryKey
    val username: String,

    @ColumnInfo
    var dubstep : Boolean = false,

    @ColumnInfo
    var melodicDubstep: Boolean = false,

    @ColumnInfo
    var loFi: Boolean = false,

    @ColumnInfo
    var chillstep: Boolean = false,

    @ColumnInfo
    var futureGarage: Boolean = false,

    @ColumnInfo
    var pianoAmbient: Boolean = false,

    @ColumnInfo
    var experimentalBass: Boolean = false,

    @ColumnInfo
    var liquidDnB: Boolean = false,

    @ColumnInfo
    var ambientBass: Boolean = false,

    @ColumnInfo
    var metalcore: Boolean = false,

    @ColumnInfo
    var acousticBallads: Boolean = false,

    @ColumnInfo
    var instrumentalRock: Boolean = false,

    @ColumnInfo
    var deathMetal: Boolean = false,

    @ColumnInfo
    var livePerformances: Boolean = false

)