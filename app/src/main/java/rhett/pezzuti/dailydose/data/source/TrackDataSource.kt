package rhett.pezzuti.dailydose.data.source

import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    suspend fun getTracks(): List<Track>

}