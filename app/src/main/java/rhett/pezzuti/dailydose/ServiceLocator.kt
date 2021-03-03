package rhett.pezzuti.dailydose

import android.content.Context
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.local.getInstance

// Necessary to share repository between test and androidTest packages?
object ServiceLocator {

    @Volatile
    var defaultTrackRepository: DefaultTrackRepository? = null

    fun provideTrackRepository(context: Context): DefaultTrackRepository {
        synchronized(this) {
            return defaultTrackRepository ?: createTrackRepository(context)
        }
    }

    private
    fun createTrackRepository(context: Context): DefaultTrackRepository {

        // val newRepo = DefaultTrackRepository()
        return DefaultTrackRepository(getInstance(context))
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