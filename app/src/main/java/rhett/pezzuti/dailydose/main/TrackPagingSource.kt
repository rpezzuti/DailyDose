package rhett.pezzuti.dailydose.main

import androidx.paging.PagingSource
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackRepository
import timber.log.Timber
import java.lang.Exception

// NEED
// Paging key (Long)
// Type of data (Track)
// Where the data is coming from

class TrackPagingSource(private val trackRepository: TrackRepository) : PagingSource<Long, Track>() {


    /**
     * Calls getAllTracks() from the repository, and returns the list as the data.
     * PrevKey and NextKey are null
     */
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
}