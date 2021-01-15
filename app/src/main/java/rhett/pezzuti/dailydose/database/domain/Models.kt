package rhett.pezzuti.dailydose.database.domain

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
    val timestamp: Long = System.currentTimeMillis()
)