package rhett.pezzuti.dailydose.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rhett.pezzuti.dailydose.database.DatabaseTrack
import rhett.pezzuti.dailydose.databinding.TrackListItemBinding

class TrackAdapter(val clickListener: DatabaseTrackListener) : ListAdapter<DatabaseTrack, ViewHolder>(TrackDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

class ViewHolder private constructor(val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DatabaseTrack, clickListener: DatabaseTrackListener) {

        binding.track = item
        binding.clickListener = clickListener
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

class TrackDiffCallback : DiffUtil.ItemCallback<DatabaseTrack>() {

    override fun areItemsTheSame(oldItem: DatabaseTrack, newItem: DatabaseTrack): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: DatabaseTrack, newItem: DatabaseTrack): Boolean {
        return oldItem == newItem
    }
}

class DatabaseTrackListener(val clickListener: (trackUrl: String) -> Unit, val fabListener: (isFavorite: Boolean, trackUrl: String) -> Unit) {
    fun onClick(track: DatabaseTrack) = clickListener(track.url)
    fun onFavorite(track: DatabaseTrack) = fabListener(track.favorite, track.url)
}
