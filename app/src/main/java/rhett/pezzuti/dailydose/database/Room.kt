package rhett.pezzuti.dailydose.database

import android.content.Context
import androidx.room.*

@Dao
interface TrackDatabaseDao {

//    @Insert
//    fun addTrack()
//
//    @Insert
//    fun addTrackToGenre()
//
//    @Update
//    fun updateTrack()
//
//    @Update
//    fun addTrackToFavorites()
//
//
//    fun getAllTracks()
//
//    fun getAllFromGenre()

    @Query("DELETE FROM track_table")
    fun clearAll()
}

@Database(entities = [DatabaseTrack::class], version = 1)
abstract class TrackDatabase : RoomDatabase() {
    abstract val trackDatabaseDao : TrackDatabaseDao
}

private lateinit var INSTANCE: TrackDatabase

fun getDatabase(context: Context) : TrackDatabase {
    synchronized(TrackDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                TrackDatabase::class.java,
                "tracks").build()
        }
    }
    return INSTANCE
}


