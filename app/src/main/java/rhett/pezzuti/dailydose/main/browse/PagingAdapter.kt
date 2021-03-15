package rhett.pezzuti.dailydose.main.browse


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import rhett.pezzuti.dailydose.adapters.FabListener
import rhett.pezzuti.dailydose.adapters.TrackListener
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.main.TrackViewHolder


// COMPONENTS: PagedList and DataSource
// 1. Define the data source
// 2. Build and configure the paging data, and give it the dataSource
// 3. Now that pagingData is built, we can request pagingData as flow from the repository.
// This is done and cached in the viewModell.
// 4. Create the adapter to bind the PagingData with the RecyclerView. ie. extend PagingDataAdapter instead of ListAdapter.

class PagingAdapter(val clickListener: TrackListener, val fabListener: FabListener) : PagingDataAdapter<Track, TrackViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, clickListener, fabListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder.from(parent)
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
                oldItem.timestamp == newItem.timestamp

            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
                oldItem == newItem
        }
    }
}