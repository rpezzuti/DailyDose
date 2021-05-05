package rhett.pezzuti.dailydose.main

import androidx.recyclerview.widget.DiffUtil
import rhett.pezzuti.dailydose.data.Track

class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}