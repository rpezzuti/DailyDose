package rhett.pezzuti.dailydose.data.source.remote

import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.source.TrackDataSource

class TrackRemoteDataSource : TrackDataSource {

    override fun getTracks(): List<Track> {
        // This is where all the retrofit code would go.

        return emptyList()
    }
}