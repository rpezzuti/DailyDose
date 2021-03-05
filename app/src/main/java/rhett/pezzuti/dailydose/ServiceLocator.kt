package rhett.pezzuti.dailydose

import android.content.Context
import androidx.room.Room
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.TrackRepository
import rhett.pezzuti.dailydose.data.source.local.TrackDatabase
import rhett.pezzuti.dailydose.data.source.local.TrackLocalDataSource
import rhett.pezzuti.dailydose.data.source.remote.TrackRemoteDataSource

object ServiceLocator {

    private var database: TrackDatabase? = null
    @Volatile
    var trackRepository: TrackRepository? = null

    fun provideTrackRepository(context: Context): TrackRepository {
        synchronized(this) {
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

}

