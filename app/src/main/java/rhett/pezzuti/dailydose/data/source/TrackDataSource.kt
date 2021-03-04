package rhett.pezzuti.dailydose.data.source

import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    suspend fun getTracks(): List<Track>
    suspend fun addTracks(tracks: List<Track>)


    suspend fun addTrack(track: Track)
    suspend fun getTrack(trackKey: Long)

    suspend fun updateTrack(trackId: Long)
}