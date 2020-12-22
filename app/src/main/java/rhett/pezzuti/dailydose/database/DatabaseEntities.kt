package rhett.pezzuti.dailydose.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_info_table")
data class DatabaseTrack (

    @PrimaryKey (autoGenerate = true)
    val dateOrTimePosted: Long = 0L,

    val trackName: String,
    val trackArtist: String,
    val trackImage: String,
    val trackUrl: String,
)