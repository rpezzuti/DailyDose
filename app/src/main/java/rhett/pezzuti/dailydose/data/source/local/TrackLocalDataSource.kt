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


    /**
     * Takes remote data and inserts it into the database.
     * Then updates the remote data to identify favorites based on the local data.
     */
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






    /**
     *
     */
    override suspend fun favoriteTrack(timestamp: Long) {
        withContext(ioDispatcher) {
            val tempTrack = trackDao.getTrack(timestamp)
            tempTrack.favorite = true
            trackDao.update(tempTrack)
        }
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        withContext(ioDispatcher) {
            val tempTrack = trackDao.getTrack(timestamp)
            tempTrack.favorite = false
            trackDao.update(tempTrack)
        }
    }






    /**
     *
     */
    override fun observeFavorites(): LiveData<List<Track>> {
        return trackDao.observeFavorites(true).map {
            it.asDomainModel()
        }
    }

    override suspend fun getFavorites(): List<Track> {
        return trackDao.getFavorites(true).asDomainModel()
    }





    /**
     *
     */
    override fun observeRecent(): LiveData<List<Track>>  {
        return trackDao.observeRecent().map {
            it.asDomainModel()
        }
    }

    override suspend fun getRecent(): List<Track> {
        return trackDao.getRecent().asDomainModel()
    }








    /**
     *
     */
    override fun observeGenre(genre: String): LiveData<List<Track>> {
        return trackDao.observeGenre(genre).map {
            it.asDomainModel()
        }
    }

    override suspend fun getGenre(genre: String): List<Track> {
        return trackDao.getGenre(genre).asDomainModel()
    }












    /**
     *
     */
    override suspend fun addTrack(track: Track) {
        withContext(ioDispatcher) {
            trackDao.insert(track.asDatabaseModel())
        }
    }




    override suspend fun addAllTracks(tracks: List<Track>) {
        withContext(ioDispatcher) {
            trackDao.insertAll(*tracks.asDatabaseModel())
        }
    }





    /**
     *
     */
    override suspend fun getTrack(trackKey: Long): Track {
        return trackDao.getTrack(trackKey).asDomainModel()
    }

    override suspend fun getAllTracks(): List<Track> {
        return trackDao.getAllTracks().asDomainModel()
    }







    /**
     *
     */
    override fun observeTrack(trackKey: Long): LiveData<Track> {
        return trackDao.observeTrack(trackKey).map {
            it.asDomainModel()
        }
    }

    override fun observeAllTracks(): LiveData<List<Track>> {
        return trackDao.observeAllTracks().map {
            it.asDomainModel()
        }
    }









    /**
     *
     */
    override suspend fun updateTrack(track: Track) {
        withContext(ioDispatcher) {
            trackDao.update(track.asDatabaseModel())
        }
    }

    override suspend fun updateAllTracks(tracks: List<Track>) {
        TODO("Not yet implemented")
    }






    /**
     *
     */
    override suspend fun deleteTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSelected(tracks: List<Track>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTotal() {
        withContext(ioDispatcher) {
            trackDao.clearAll()
        }
    }

}