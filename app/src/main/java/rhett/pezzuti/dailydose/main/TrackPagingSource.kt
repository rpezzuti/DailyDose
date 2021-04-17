package rhett.pezzuti.dailydose.main

import androidx.paging.PagingSource
import retrofit2.awaitResponse
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.network.BrowseFirebaseApiService
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.utils.asListOfTracks
import java.lang.Exception

// NEED
// Paging key (Long)
// Type of data (Track)
// Where the data is coming from

// THIS IS JUST ANOTHER SOURCE OF DATA.
class TrackPagingSource(private val firebaseService: BrowseFirebaseApiService) : PagingSource<Long, Track>() {


    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Track> {
        return try {

            val trackData = BrowseFirebaseGson.retrofitService.getAllTracks().awaitResponse().body()
                .asListOfTracks()

            LoadResult.Page(
                data = trackData,
                null,
                null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
// Repo Version
/*

class TrackPagingSourceRepo(private val trackRepository: TrackRepository) : PagingSource<Long, Track>() {


    */
/**
     * Calls getAllTracks() from the repository, and returns the list as the data.
     * PrevKey and NextKey are null
     *//*

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Track> {

        val thePlaylist = trackRepository.getAllTracks()

        return try {
            LoadResult.Page(
                data = thePlaylist,
                null,
                null
            )
        } catch (e: Exception) {
            Timber.i("PAGING LOADING ERROR: $e")
            return LoadResult.Error(e)
        }

    }
}*/
