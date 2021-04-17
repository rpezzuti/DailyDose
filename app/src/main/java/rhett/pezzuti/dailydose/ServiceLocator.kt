package rhett.pezzuti.dailydose

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import kotlinx.coroutines.runBlocking
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.TrackRepository
import rhett.pezzuti.dailydose.data.source.local.TrackDatabase
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource

object ServiceLocator {

    private val lock = Any()

    private var database: TrackDatabase? = null
    @Volatile           // Since it could be used on multiple threads.
    var trackRepository: TrackRepository? = null
        @VisibleForTesting set                      // In normal code, we dont need repo's setter, but for testing we do.


    fun provideTrackRepository(context: Context): TrackRepository {

        // Return repository, or create one if null.
        // ?: is a null check

        synchronized(lock) {
            return trackRepository ?: createTrackRepository(context)
        }
    }

    private
    fun createTrackRepository(context: Context): TrackRepository {
        val newRepo = DefaultTrackRepository(createLocalDataSource(context), TrackRemoteDataSource)
        trackRepository = newRepo
        return newRepo
    }

    private
    fun createLocalDataSource(context: Context): TrackLocalDataSource {
        val database = database ?: createDatabase(context)
        return TrackLocalDataSource(database.trackDatabaseDao)
    }

    private
    fun createDatabase(context: Context) : TrackDatabase {
        val result = Room.databaseBuilder(
            context,
            TrackDatabase::class.java,
            "Track.db"
        ).build()
        database = result
        return result
    }


    @VisibleForTesting
    fun resetDatabase() {
        synchronized(lock) {
            runBlocking {
                TrackRemoteDataSource.deleteAllTotal()
            }
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            trackRepository = null
        }
    }
}

