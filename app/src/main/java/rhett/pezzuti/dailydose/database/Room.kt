package rhett.pezzuti.dailydose.database

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.work.Data
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack

@Dao
interface TrackDatabaseDao {

    @Insert
    fun insert(track: DatabaseTrack)

    @Query ("SELECT * FROM track_table LIMIT 5")
    fun getRecentTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table")
    fun getAllTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getAllFromGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("DELETE FROM track_table")
    fun clearAll()
}

@Database(entities = [DatabaseTrack::class], version = 3, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract val trackDatabaseDao : TrackDatabaseDao
}

private lateinit var INSTANCE: TrackDatabase

fun initializeDatabase(context: Context) : TrackDatabase {
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

fun getInstance(context: Context) : TrackDatabase {
    synchronized(TrackDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                TrackDatabase::class.java,
                "tracks").build()
        }
    }
    return INSTANCE
}


