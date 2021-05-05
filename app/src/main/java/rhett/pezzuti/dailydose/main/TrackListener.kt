package rhett.pezzuti.dailydose.main

import rhett.pezzuti.dailydose.data.Track

class TrackListener(val trackListener: (trackUrl: String) -> Unit) {
    fun onClick(track: Track) = trackListener(track.url)
}