package rhett.pezzuti.dailydose.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.adapters.TrackAdapter
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentBrowseBinding
import rhett.pezzuti.dailydose.factory.BrowseViewModelFactory
import rhett.pezzuti.dailydose.viewmodels.BrowseViewModel


class BrowseFragment : Fragment() {

    private lateinit var binding: FragmentBrowseBinding
    private val viewModel: BrowseViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        val trackDataSource = getInstance(activity.applicationContext).trackDatabaseDao
        ViewModelProvider(this, BrowseViewModelFactory(trackDataSource, activity.application)).get(BrowseViewModel::class.java)
    }

    private var viewModelAdapter: TrackAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        viewModel.playlist.observe(viewLifecycleOwner, { tracks ->
            tracks?.apply {
                //viewModelAdapter?.submitList(tracks)
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


        return binding.root
    }



}