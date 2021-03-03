package rhett.pezzuti.dailydose.data.source

import rhett.pezzuti.dailydose.data.Track

interface TrackDataSource {

    fun getTracks(): List<Track>

}