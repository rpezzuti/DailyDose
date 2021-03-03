package rhett.pezzuti.dailydose

import android.content.Context
import rhett.pezzuti.dailydose.data.TrackRepository
import rhett.pezzuti.dailydose.data.getInstance

object ServiceLocator {

    @Volatile
    var trackRepository: TrackRepository? = null

    fun provideTrackRepository(context: Context): TrackRepository {
        synchronized(this) {
            return trackRepository ?: createTrackRepository(context)
        }
    }

    private
    fun createTrackRepository(context: Context): TrackRepository {

        // val newRepo = TrackRepository()
        return TrackRepository(getInstance(context))
    }

    private
    fun createTrackDataSource(context: Context) {
        // Implement
    }

    private
    fun createDatabase() {
        // Implement
    }


}