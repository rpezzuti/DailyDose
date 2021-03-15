package rhett.pezzuti.dailydose.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.main.TrackViewHolder

class TrackAdapter(val clickListener: TrackListener, val fabListener: FabListener) : ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, fabListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

}



class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}

class TrackListener(val clickListener: (trackUrl: String) -> Unit) {
    fun onClick(track: Track) = clickListener(track.url)
}

class FabListener(val fabListener: (isFavorite: Boolean, trackId: Long) -> Unit ) {
    fun onFavorite(track: Track) = fabListener(track.favorite, track.timestamp)
}
