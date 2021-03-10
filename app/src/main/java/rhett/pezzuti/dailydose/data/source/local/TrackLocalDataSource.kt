package rhett.pezzuti.dailydose.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.asDatabaseModel
import rhett.pezzuti.dailydose.data.asDomainModel
import rhett.pezzuti.dailydose.data.source.TrackDataSource

class TrackLocalDataSource(
    private val trackDao: TrackDatabaseDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TrackDataSource {

    /** Multiple Tracks **/
    override fun getTracks(): LiveData<List<Track>> {
        return trackDao.getAllTracks().map {
            it.asDomainModel()
        }
    }

    override suspend fun addTracks(tracks: List<Track>) {
        withContext(ioDispatcher) {
            trackDao.insertAll(*tracks.asDatabaseModel())
        }
    }

    /** Recent Tracks **/
    override fun observeRecent(): LiveData<List<Track>>  {
        return trackDao.getRecentTracks().map {
            it.asDomainModel()
        }
    }

    /** Favorites **/
    override fun observeFavorites(): LiveData<List<Track>> {
        return trackDao.getFavorites(true).map {
            it.asDomainModel()
        }
    }

    /** Single Tracks **/
    override suspend fun getTrack(trackKey: Long): Track {
        return trackDao.getTrack(trackKey).asDomainModel()
    }

    override suspend fun updateTrack(track: Track) {
        withContext(ioDispatcher) {
            trackDao.update(track.asDatabaseModel())
        }
    }

    override suspend fun addTrack(track: Track) {
        withContext(ioDispatcher) {
            trackDao.insert(track.asDatabaseModel())
        }
    }

    /** Track Manipulation **/
    override suspend fun favorite(trackId: Long) {
        withContext(ioDispatcher) {
            val tempTrack = trackDao.getTrack(trackId)
            tempTrack.favorite = true
            trackDao.update(tempTrack)
        }
    }

    override suspend fun unFavorite(trackId: Long) {
        withContext(ioDispatcher) {
            val tempTrack = trackDao.getTrack(trackId)
            tempTrack.favorite = false
            trackDao.update(tempTrack)
        }
    }

    override suspend fun deleteAllTracks() {
        withContext(ioDispatcher) {
            trackDao.clearAll()
        }
    }

    override suspend fun syncTracks(remoteData: List<Track>) {
        withContext(ioDispatcher) {
            val saved = trackDao.getFavoritesToSave(true)
            trackDao.clearAll()
            remoteData.forEach { track ->
                trackDao.insert(track.asDatabaseModel())
            }
            saved.forEach { track ->
                trackDao.update(track)
            }
        }
    }

    /** Unused in Local **/
    override suspend fun refreshTracks(): List<Track> {
        TODO("Not yet implemented")
    }
}