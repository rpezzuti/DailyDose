package rhett.pezzuti.dailydose.database

import androidx.room.*

@Entity(tableName = "song_info_table")
data class DatabaseTrack (

    @PrimaryKey (autoGenerate = true)
    val dateOrTimePosted: Long = 0L
)

//@Database (entities = [DatabaseTrack::class], version = 1, exportSchema = false)
//class TrackDatabase : RoomDatabase() {
//
//
//
//
//}