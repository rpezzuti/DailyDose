package rhett.pezzuti.dailydose.database

import androidx.room.*

@Entity(tableName = "song_info_table")
data class DatabaseTrack (

    @PrimaryKey (autoGenerate = true)
    val dateOrTimePosted: Long = 0L
)


@Entity(tableName = "track_list_table")
data class DatabaseTrackList (

    @PrimaryKey
    val url: String

    )