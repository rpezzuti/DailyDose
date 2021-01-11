package rhett.pezzuti.dailydose.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack

@BindingAdapter("trackTitle")
fun TextView.setTrackTitle(item: DatabaseTrack?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("trackArtist")
fun TextView.setTrackArtist(item: DatabaseTrack?) {
    item?.let {
        text = item.artist
    }
}

@BindingAdapter("trackGenre")
fun TextView.setTrackGenre(item: DatabaseTrack?) {
    item?.let {
        text = "Genre: ${item.genre}"
    }
}
