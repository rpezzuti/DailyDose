package rhett.pezzuti.dailydose.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.main.browse.BrowseStatus

// The value is what determines the name in the XML code
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

@BindingAdapter("isFavorite")
fun FloatingActionButton.favorite(item: Track?) {
    item?.let {
        when(item.favorite){
            false -> setImageResource(R.drawable.ic_favorite_border_24px)
            true -> setImageResource(R.drawable.ic_heart)
        }
    }
}

@BindingAdapter("setGenre")
fun TextView.setGenre(item: Track?) {
    item?.let {
        when (item.genre)  {
            context.getString(R.string.TOPIC_TEST) -> text = context.getString(R.string.TOPIC_TEST)

            context.getString(R.string.TOPIC_DUBSTEP) -> text = context.getString(R.string.genre_dubstep)
            context.getString(R.string.TOPIC_MELODIC_DUBSTEP) -> text = context.getString(R.string.genre_melodic_dubstep)
            context.getString(R.string.TOPIC_LO_FI) -> text = context.getString(R.string.genre_lo_fi)
            context.getString(R.string.TOPIC_CHILLSTEP) -> text = context.getString(R.string.genre_chillstep)
            context.getString(R.string.TOPIC_GARAGE) -> text = context.getString(R.string.genre_garage)
            context.getString(R.string.TOPIC_PIANO_AMBIENT) -> text = context.getString(R.string.genre_piano_ambient)
            context.getString(R.string.TOPIC_EXPERIMENTAL_BASS) -> text = context.getString(R.string.genre_experimental_bass)
            context.getString(R.string.TOPIC_LIQUID_DNB) -> text = context.getString(R.string.genre_liquid_dnb)
            context.getString(R.string.TOPIC_AMBIENT_BASS) -> text = context.getString(R.string.genre_ambient_bass)

            context.getString(R.string.TOPIC_METALCORE) -> text = context.getString(R.string.genre_metalcore)
            context.getString(R.string.TOPIC_ACOUSTIC_BALLADS) -> text = context.getString(R.string.genre_acoustic_ballads)
            context.getString(R.string.TOPIC_INSTRUMENTAL_ROCK) -> text = context.getString(R.string.genre_instrumental_rock)
            context.getString(R.string.TOPIC_DEATH_METAL) -> text = context.getString(R.string.genre_death_metal)
            context.getString(R.string.TOPIC_LIVE_PERFORMANCES) -> text = context.getString(R.string.genre_live_performances)
        }

    }
}

@BindingAdapter("setImage")
fun ImageView.setImage(item: Track?) {
    item?.let {
        when (item.genre) {
            context.getString(R.string.TOPIC_DUBSTEP) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_MELODIC_DUBSTEP) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_LO_FI) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_CHILLSTEP) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_GARAGE) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_PIANO_AMBIENT) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_EXPERIMENTAL_BASS) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_LIQUID_DNB) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_AMBIENT_BASS) -> setImageResource(R.drawable.ic_eigth_note)

            context.getString(R.string.TOPIC_METALCORE) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_ACOUSTIC_BALLADS) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_INSTRUMENTAL_ROCK) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_DEATH_METAL) -> setImageResource(R.drawable.ic_eigth_note)
            context.getString(R.string.TOPIC_LIVE_PERFORMANCES) -> setImageResource(R.drawable.ic_eigth_note)
        }
    }
}

@BindingAdapter("browseStatus")
fun ImageView.browseStatus(status: BrowseStatus) {
    when (status) {
        BrowseStatus.LOADING -> {
            visibility = View.GONE
        }
        BrowseStatus.DONE -> {
            visibility = View.GONE
        }
        BrowseStatus.ERROR -> {
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("progressBarStatus")
fun progressBarStatus(progressBar: ProgressBar, status: BrowseStatus) {
    when (status) {
        BrowseStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        BrowseStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
        BrowseStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Track>?) {
    val adapter = recyclerView.adapter as TrackAdapter
    adapter.submitList(data)
}