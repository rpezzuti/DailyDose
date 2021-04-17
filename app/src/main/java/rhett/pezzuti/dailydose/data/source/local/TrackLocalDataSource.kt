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
            val saved = trackDao.getFavorites(true)
            trackDao.deleteAllTotal()
            remoteData.forEach { track ->
                trackDao.addTrack(track.asDatabaseModel())
            }
            saved.forEach { track ->
                trackDao.updateTrack(track)
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
            trackDao.updateTrack(tempTrack)
        }
    }

    override suspend fun unFavoriteTrack(timestamp: Long) {
        withContext(ioDispatcher) {
            val tempTrack = trackDao.getTrack(timestamp)
            tempTrack.favorite = false
            trackDao.updateTrack(tempTrack)
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
            trackDao.addTrack(track.asDatabaseModel())
        }
    }




    override suspend fun addAllTracks(tracks: List<Track>) {
        withContext(ioDispatcher) {
            trackDao.addAllTracks(*tracks.asDatabaseModel())
        }
    }





    /**
     *
     */
    override suspend fun getTrack(timestamp: Long): Track {
        return trackDao.getTrack(timestamp).asDomainModel()
    }

    override suspend fun getAllTracks(): List<Track> {
        return trackDao.getAllTracks().asDomainModel()
    }







    /**
     *
     */
    override fun observeTrack(timestamp: Long): LiveData<Track> {
        return trackDao.observeTrack(timestamp).map {
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
            trackDao.updateTrack(track.asDatabaseModel())
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
            trackDao.deleteAllTotal()
        }
    }

}