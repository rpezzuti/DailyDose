package rhett.pezzuti.dailydose.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack

@Dao
interface TrackDatabaseDao {

    @Insert
    fun insert(track: DatabaseTrack)

    @Query ("SELECT * FROM track_table LIMIT 5")
    fun getRecentTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getAllFromGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("DELETE FROM track_table")
    fun clearAll()
}

@Database(entities = [DatabaseTrack::class], version = 4, exportSchema = false)
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




