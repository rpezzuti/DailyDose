package rhett.pezzuti.dailydose.data.source.local

import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack


@Database(entities = [DatabaseTrack::class], version = 10, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract val trackDatabaseDao : TrackDatabaseDao
}
