package rhett.pezzuti.dailydose.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.database.User

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

@BindingAdapter("welcomeUser")
fun TextView.setWelcomeHome(item: User?) {
    item?.let {
        text = "~ Welcome, ${item.username}! ~"
    }
}

@BindingAdapter("isFavorite")
fun FloatingActionButton.favorite(item: DatabaseTrack?) {
    item?.let {
        when(item.favorite){
            false -> setImageResource(R.drawable.ic_favorite_border_24px)
            true -> setImageResource(R.drawable.ic_heart)
        }
    }
}
