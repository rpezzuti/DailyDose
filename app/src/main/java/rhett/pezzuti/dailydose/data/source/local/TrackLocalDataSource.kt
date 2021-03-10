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

    /** Unused in Local **/
    override suspend fun refreshTracks(): List<Track> {
        TODO("Not yet implemented")
    }



    /** Query All **/
    override fun observeAllTracks(): LiveData<List<Track>> {
        return trackDao.observeAllTracks().map {
            it.asDomainModel()
        }
    }
    override suspend fun getAllTracks(): List<Track> {
        return trackDao.getAllTracks().asDomainModel()
    }




    /** Query Recent **/
    override fun observeRecent(): LiveData<List<Track>>  {
        return trackDao.observeRecent().map {
            it.asDomainModel()
        }
    }
    override suspend fun getRecent(): List<Track> {
        return trackDao.getRecent().asDomainModel()
    }





    /** Query Favorites **/
    override fun observeFavorites(): LiveData<List<Track>> {
        return trackDao.observeFavorites(true).map {
            it.asDomainModel()
        }
    }

    override suspend fun getFavorites(): List<Track> {
        return trackDao.getFavorites(true).asDomainModel()
    }







    /** Single Tracks **/
    override suspend fun getTrack(trackKey: Long): Track {
        return trackDao.getTrack(trackKey).asDomainModel()
    }

    override fun observeTrack(trackKey: Long): LiveData<Track> {
        return trackDao.observeTrack(trackKey).map {
            it.asDomainModel()
        }
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




    override suspend fun addAllTracks(tracks: List<Track>) {
        withContext(ioDispatcher) {
            trackDao.insertAll(*tracks.asDatabaseModel())
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
}