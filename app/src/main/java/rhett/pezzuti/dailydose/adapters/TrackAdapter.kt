package rhett.pezzuti.dailydose.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.databinding.TrackListItemBinding

class TrackAdapter(val clickListener: TrackListener, val fabListener: FabListener) : ListAdapter<Track, ViewHolder>(TrackDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, fabListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

class ViewHolder private constructor(val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Track, clickListener: TrackListener, fabListener: FabListener) {

        binding.track = item
        binding.clickListener = clickListener
        binding.fabListener = fabListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            /** we're inflating a new view and returning it inside of a holder **/
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TrackListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
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

class FabListener(val fabListener: (isFavorite: Boolean, trackUrl: String) -> Unit ) {
    fun onFavorite(track: Track) = fabListener(track.favorite, track.url)
}
