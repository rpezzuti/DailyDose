package rhett.pezzuti.dailydose.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.domain.DatabaseTrack
import rhett.pezzuti.dailydose.databinding.TrackListItemBinding

class TrackAdapter : ListAdapter<DatabaseTrack, ViewHolder>(TrackDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

class ViewHolder private constructor (val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val trackTitle: TextView = itemView.findViewById(R.id.song_item_track_title)
    val trackArtist: TextView = itemView.findViewById(R.id.song_item_track_artist)
    val trackAlbumImage: ImageView = itemView.findViewById(R.id.song_item_album_image)

    fun bind(item: DatabaseTrack) {

        trackTitle.text = item.title
        trackArtist.text = item.artist
        trackAlbumImage.setImageResource(R.drawable.ic_eigth_note)
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

class DatabaseTrackListener(val clickListener: (trackUrl: String) -> Unit) {
    fun onClick(track: DatabaseTrack) = clickListener(track.url)
}
