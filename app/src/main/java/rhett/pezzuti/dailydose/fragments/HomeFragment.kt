package rhett.pezzuti.dailydose.fragments

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import rhett.pezzuti.dailydose.viewmodels.HomeViewModel
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.DatabaseTrackListener
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.HomeFragmentBinding
import rhett.pezzuti.dailydose.factory.HomeViewModelFactory
import timber.log.Timber

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    /**
     *                              TODO BLOCK
     *
     * - Use Firebase as backend.
     *
     *
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )

        Timber.i("Current Timestamp: ${System.currentTimeMillis()}")

        /** Normal Pipes **/
        val app = requireNotNull(this.activity).application
        val dataSource = getInstance(app.applicationContext).trackDatabaseDao
        viewModelFactory = HomeViewModelFactory(dataSource, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.homeViewModelXML = viewModel
        binding.lifecycleOwner = this


        /** Recycler View Pipes **/
        val adapter = TrackAdapter( DatabaseTrackListener { url ->
            val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val contentPendingIntent = PendingIntent.getActivity(
                app.applicationContext,
                0,
                contentIntent,
                PendingIntent.FLAG_ONE_SHOT
            ).send()
        }

        )
        binding.homeRecyclerView.adapter = adapter
        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            tracks?.let {
                adapter.submitList(tracks)
            }
        })


        viewModel.eventFavorites.observe(viewLifecycleOwner, { event ->
            if (event == true){
                Timber.i("called")
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())
                viewModel.doneNavigatingFavorites()
            }
        })

        viewModel.eventUpload.observe(viewLifecycleOwner, { event ->
            if (event == true){
                Timber.i("called")
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUploadFragment())
                viewModel.doneNavigatingUpload()
            }
        })

        viewModel.eventPreferences.observe(viewLifecycleOwner, { event ->
            if (event == true){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPreferencesFragment())
                viewModel.doneNavigatingPreferences()
            }

        })

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, { event ->
            if (event == true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackBar()
            }
        })


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


