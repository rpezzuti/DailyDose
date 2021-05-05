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
import androidx.recyclerview.widget.DividerItemDecoration
import rhett.pezzuti.dailydose.DailyDoseApplication
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.main.FabListener
import rhett.pezzuti.dailydose.main.TrackAdapter
import rhett.pezzuti.dailydose.main.TrackListener
import rhett.pezzuti.dailydose.databinding.FragmentBrowseBinding
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

        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            viewModelAdapter?.submitList(tracks)
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

        binding.browseRecyclerView.adapter = viewModelAdapter
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