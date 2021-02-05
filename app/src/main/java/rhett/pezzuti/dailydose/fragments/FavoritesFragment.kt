package rhett.pezzuti.dailydose.fragments

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import rhett.pezzuti.dailydose.viewmodels.FavoritesViewModel
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.DatabaseTrackListener
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentFavoritesBinding
import rhett.pezzuti.dailydose.factory.FavoritesViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var viewModelFactory: FavoritesViewModelFactory
    private lateinit var binding: FragmentFavoritesBinding


    private var viewModelAdapter: TrackAdapter? = null

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

        val app = requireNotNull(this.activity).application
        val trackDataSource = getInstance(app.applicationContext).trackDatabaseDao
        val userDataSource = getInstance(app.applicationContext).userPreferencesDao

        viewModelFactory = FavoritesViewModelFactory(trackDataSource, userDataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        binding.favoritesViewModelXML = viewModel
        binding.lifecycleOwner = this



        viewModelAdapter = TrackAdapter( DatabaseTrackListener { url ->
            val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val contentPendingIntent = PendingIntent.getActivity(
                requireContext(),
                0,
                contentIntent,
                PendingIntent.FLAG_ONE_SHOT
            ).send()

        })
        binding.favoritesRecyclerView.adapter = viewModelAdapter



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_favorites_title)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // This return statement works when the id of the menu item matches the id of the fragment in the nav graph.
        return NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}