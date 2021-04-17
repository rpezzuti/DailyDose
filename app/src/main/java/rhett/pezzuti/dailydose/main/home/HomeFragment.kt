package rhett.pezzuti.dailydose.main.home

import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import rhett.pezzuti.dailydose.DailyDoseApplication
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.FabListener
import rhett.pezzuti.dailydose.adapters.TrackListener
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.data.source.local.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentHomeBinding
import rhett.pezzuti.dailydose.settings.SettingsActivity
import timber.log.Timber
import java.lang.Exception

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var viewModelAdapter: TrackAdapter? = null

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory((requireContext().applicationContext as DailyDoseApplication).trackRepository)
    }

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
            R.layout.fragment_home,
            container,
            false
        )
        binding.tvHomeWelcome.text = "~ Welcome, ${this.activity?.getSharedPreferences(getString(R.string.user_preferences_key), Context.MODE_PRIVATE)?.getString("username", "bob")}! ~"

        binding.homeViewModelXML = viewModel
        binding.lifecycleOwner = this

        viewModel.refreshTracks()
        setupAdapter()

        // SO NICE. Adds divider between the RV items.
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.homeRecyclerView.addItemDecoration(decoration)
        binding.homeRecyclerView.adapter = viewModelAdapter



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_home_title)
        setHasOptionsMenu(true)
        return binding.root
    }


    private
    fun setupAdapter() {
        /** Recycler View OnClick function **/
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
            } catch (exception: Exception) {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                Timber.i("Exception Found: $exception")
                Timber.i("Exception Found: ${exception.message}")
                Timber.i("Exception Found: ${exception.message.toString()}")
                Timber.i("Exception Found: ${exception.stackTraceToString()}")
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // This return statement works when the id of the menu item matches the id of the fragment in the nav graph.

        return if (item.itemId == R.id.settingsActivity){
            val intent = Intent(this.activity, SettingsActivity::class.java)
            startActivity(intent)
            return true
        } else {
                // For the overflow menu
            (NavigationUI.onNavDestinationSelected(item!!, requireView().findNavController())
                    || super.onOptionsItemSelected(item))
        }
    }

}


