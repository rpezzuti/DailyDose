package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.asDomainModel
import rhett.pezzuti.dailydose.data.domain.Track
import rhett.pezzuti.dailydose.data.source.local.ClientDatabase


class DefaultTrackRepository(private val database: ClientDatabase) : ITrackRepository {


    // Public access of all the tracks in the database.
    // Fetching all the DATABASE tracks and returning them as DOMAIN tracks
    val tracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getAllTracks())
        {
            it.asDomainModel()
        }

    val recentTracks: LiveData<List<Track>> =
        Transformations.map(database.trackDatabaseDao.getRecentTracks())
        {
            it.asDomainModel()
        }

    var favorites = listOf<DatabaseTrack>()
}