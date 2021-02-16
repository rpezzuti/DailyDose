package rhett.pezzuti.dailydose.database

import android.content.Context
import androidx.room.*


@Database(entities = [DatabaseTrack::class], version = 8, exportSchema = false)
abstract class ClientDatabase : RoomDatabase() {
    abstract val trackDatabaseDao : TrackDatabaseDao
}

private lateinit var INSTANCE: ClientDatabase

fun getInstance(context: Context) : ClientDatabase {
    synchronized(ClientDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ClientDatabase::class.java,
                "tracks")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}

