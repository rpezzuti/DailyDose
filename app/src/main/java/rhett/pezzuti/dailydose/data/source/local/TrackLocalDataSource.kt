package rhett.pezzuti.dailydose.data.source.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.asDatabaseModel
import rhett.pezzuti.dailydose.data.source.TrackDataSource

class TrackLocalDataSource(
    private val trackDao: TrackDatabaseDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackDataSource {


    /** Multiple Tracks **/
    override suspend fun getTracks(): List<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun addTracks(tracks: List<Track>) {
        withContext(ioDispatcher) {
            trackDao.insertAll(*tracks.asDatabaseModel())
        }
    }

    /** Single Tracks **/
    override suspend fun getTrack(trackKey: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun addTrack(track: Track) {
        withContext(ioDispatcher) {
            trackDao.insert(track.asDatabaseModel())
        }
    }
}