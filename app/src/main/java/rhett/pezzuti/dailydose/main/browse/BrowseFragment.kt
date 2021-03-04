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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.FabListener
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.adapters.TrackListener
import rhett.pezzuti.dailydose.data.source.local.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentBrowseBinding
import timber.log.Timber
import java.lang.Exception


class BrowseFragment : Fragment() {

    private lateinit var binding: FragmentBrowseBinding
    private val viewModel: BrowseViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        val trackDataSource = getInstance(activity.applicationContext).trackDatabaseDao
        ViewModelProvider(this, BrowseViewModelFactory(trackDataSource, activity.application)).get(
            BrowseViewModel::class.java)
    }

    private var viewModelAdapter: TrackAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tracks.observe(viewLifecycleOwner, { playlist ->
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
        binding.browseRecyclerView.adapter = viewModelAdapter

        makeChips()



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_browse_title)
        return binding.root
    }


    private
    fun makeChips() {
        // Dummy List Data
        val dummyList = listOf("Dubstep", "Melodic Dubstep", "Your Mum")

        // Make a new Chip Group
        val chipGroup = binding.browseChipGroup

        // Inflate it
        val inflater = LayoutInflater.from(chipGroup.context)

        // Iterate over the list and make a new chip child for each string (genre) in the list
        val children = dummyList.map { genre ->
            val chip = inflater.inflate(R.layout.genre_chip, chipGroup, false) as Chip
            chip.text = genre
            chip.tag = genre
            chip
        }

        chipGroup.removeAllViews()
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }

}