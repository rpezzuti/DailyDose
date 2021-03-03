package rhett.pezzuti.dailydose.data.source.local

import android.content.Context
import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack



@Database(entities = [DatabaseTrack::class], version = 9, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract val trackDatabaseDao : TrackDatabaseDao
}

private lateinit var INSTANCE: TrackDatabase

fun getInstance(context: Context) : TrackDatabase {
    synchronized(TrackDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                TrackDatabase::class.java,
                "tracks")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}

