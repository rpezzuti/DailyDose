package rhett.pezzuti.dailydose.main.favorites

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import rhett.pezzuti.dailydose.DailyDoseApplication
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.main.FabListener
import rhett.pezzuti.dailydose.main.TrackListener
import rhett.pezzuti.dailydose.main.TrackAdapter
import rhett.pezzuti.dailydose.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var viewModelAdapter: TrackAdapter? = null

    private val viewModel by viewModels<FavoritesViewModel> {
        FavoritesViewModelFactory((requireContext().applicationContext as DailyDoseApplication).trackRepository)
    }

    // Putting the data into the adapter at the appropriate time.
    /**
     * Called immediately after onCreateView() has returned, and fragment's
     * view hierarchy has been created.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            tracks?.apply {
                viewModelAdapter?.submitList(tracks)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites,
            container,
            false
        )

        binding.favoritesViewModelXML = viewModel
        binding.lifecycleOwner = this

        setupAdapter()
        setupDecoration()
        binding.favoritesRecyclerView.adapter = viewModelAdapter



        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.fragment_favorites_title)
        setHasOptionsMenu(true)
        return binding.root
    }

    private
    fun setupAdapter() {
        viewModelAdapter = TrackAdapter(TrackListener { url ->
            val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val contentPendingIntent = PendingIntent.getActivity(
                requireContext(),
                0,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            ).send()

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

    private
    fun setupDecoration() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.favoritesRecyclerView.addItemDecoration(decoration)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // This return statement works when the id of the menu item matches the id of the fragment in the nav graph.

         return (NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController())
                || super.onOptionsItemSelected(item))

    }

}