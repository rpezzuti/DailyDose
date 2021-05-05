package rhett.pezzuti.dailydose.main

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rhett.pezzuti.dailydose.data.Track

class TrackAdapter(
    val trackListener: TrackListener,
    val fabListener: FabListener
) : ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, trackListener, fabListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }

}



