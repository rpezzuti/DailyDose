package rhett.pezzuti.dailydose.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.data.domain.Track

@BindingAdapter("trackTitle")
fun TextView.setTrackTitle(item: Track?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("trackArtist")
fun TextView.setTrackArtist(item: Track?) {
    item?.let {
        text = item.artist
    }
}

@BindingAdapter("trackGenre")
fun TextView.setTrackGenre(item: Track?) {
    item?.let {
        text = "Genre: ${item.genre}"
    }
}

@BindingAdapter("isFavorite")
fun FloatingActionButton.favorite(item: Track?) {
    item?.let {
        when(item.favorite){
            false -> setImageResource(R.drawable.ic_favorite_border_24px)
            true -> setImageResource(R.drawable.ic_heart)
        }
    }
}
