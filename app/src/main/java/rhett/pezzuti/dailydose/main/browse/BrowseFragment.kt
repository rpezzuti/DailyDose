package rhett.pezzuti.dailydose.main.browse

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.DailyDoseApplication
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.FabListener
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.adapters.TrackListener
import rhett.pezzuti.dailydose.databinding.FragmentBrowseBinding
import timber.log.Timber
import java.lang.Exception


class BrowseFragment : Fragment() {

    private lateinit var binding: FragmentBrowseBinding

    private val viewModel by viewModels<BrowseViewModel> {
        /** Create ViewModel through Service Locator pattern **/
        BrowseViewModelFactory((requireContext().applicationContext as DailyDoseApplication).trackRepository)
    }

    private var viewModelAdapter: TrackAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Give the track playlist to the adapter **/
        viewModel.playlist.observe(viewLifecycleOwner, { playlist ->
            playlist?.apply {
                viewModelAdapter?.submitList(playlist)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_browse,
            container,
            false
        )
        binding.browseViewModelXML = viewModel
        binding.lifecycleOwner = this

        setupAdapter()
        setupDecoration()
        binding.browseRecyclerView.adapter = viewModelAdapter


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_browse_title)
        return binding.root
    }


    /**
     * Initializes the TrackAdapter and it's clickListeners for this layout's Recycler View.
     */
    private
    fun setupAdapter() {
        /** Recycler View OnClick functionality **/
        viewModelAdapter = TrackAdapter( TrackListener { url ->
            val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val contentPendingIntent = PendingIntent.getActivity(
                requireContext(),
                0,
                contentIntent,
                PendingIntent.FLAG_ONE_SHOT
            )
            try {
                contentPendingIntent.send()
            } catch (e: Exception) {
                Timber.i("Exception Found: ${e}")
                Timber.i("Exception Found: ${e.message}")
                Timber.i("Exception Found: ${e.message.toString()}")
                Timber.i("Exception Found: ${e.stackTrace}")
            }

        }, FabListener { favorite, timestamp ->
            if (!favorite) {
                viewModel.addToFavorites(timestamp)
                Toast.makeText(this.requireContext(), "Added to Favorites", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.removeFromFavorites(timestamp)
                Toast.makeText(this.requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    /**
     * Creates vertical divider decoration and gives it to the Recycler View.
     */
    private
    fun setupDecoration() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.browseRecyclerView.addItemDecoration(decoration)
    }

}