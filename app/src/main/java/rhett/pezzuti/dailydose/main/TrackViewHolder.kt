package rhett.pezzuti.dailydose.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.databinding.TrackListItemBinding

class TrackViewHolder private constructor(val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Track, trackListener: TrackListener, fabListener: FabListener) {

        binding.track = item
        binding.clickListener = trackListener
        binding.fabListener = fabListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            /** we're inflating a new view and returning it inside of a holder **/
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TrackListItemBinding.inflate(layoutInflater, parent, false)
            return TrackViewHolder(binding)
        }
    }
}