package rhett.pezzuti.dailydose.data.source

import androidx.lifecycle.LiveData
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track

// Methods to interact with the data.
// The database is the single source of truth!
interface TrackRepository {

    suspend fun getAllTracks(): LiveData<List<Track>>

    // When implemented concretely, calls updateTracksFromRemoteDataSource()
    suspend fun refreshTracks()

    // This would be next, to actually give the data to the view model? i believe
    //fun observeTracks(): LiveData<List<Track>>

    // Should have a buncho functions here that actually do stuff
    suspend fun getTrackByGenre(genre: String): List<Track>


    suspend fun favoriteTrack(trackId: Long)
}