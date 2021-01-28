package rhett.pezzuti.dailydose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


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
    val timestamp: Long
)

@Entity (tableName = "user_preferences")
data class User (

    @PrimaryKey
    val username: String,

    @ColumnInfo
    val dubstep : Boolean = false,

    @ColumnInfo
    val melodicDubstep: Boolean = false,

    @ColumnInfo
    val loFi: Boolean = false,

    @ColumnInfo
    val chillstep: Boolean = false,

    @ColumnInfo
    val futureGarage: Boolean = false,

    @ColumnInfo
    val pianoAmbient: Boolean = false,

    @ColumnInfo
    val experimentalBass: Boolean = false,

    @ColumnInfo
    val liquidDnB: Boolean = false,

    @ColumnInfo
    val ambientBass: Boolean = false,

    @ColumnInfo
    val metalcore: Boolean = false,

    @ColumnInfo
    val acousticBallads: Boolean = false,

    @ColumnInfo
    val instrumentalRock: Boolean = false,

    @ColumnInfo
    val deathMetal: Boolean = false,

    @ColumnInfo
    val livePerformances: Boolean = false

)