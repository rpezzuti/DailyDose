package rhett.pezzuti.dailydose.main

import rhett.pezzuti.dailydose.data.Track

class FabListener(val fabListener: (isFavorite: Boolean, trackId: Long) -> Unit ) {
    fun onFavorite(track: Track) = fabListener(track.favorite, track.timestamp)
}