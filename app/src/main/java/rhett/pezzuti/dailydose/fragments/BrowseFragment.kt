package rhett.pezzuti.dailydose.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentBrowseBinding
import rhett.pezzuti.dailydose.viewmodels.BrowseViewModel


class BrowseFragment : Fragment() {

    private lateinit var binding: FragmentBrowseBinding
    private lateinit var viewModel: BrowseViewModel

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

        viewModel = ViewModelProvider(this).get(BrowseViewModel::class.java)
        binding.browseViewModelXML = viewModel
        binding.lifecycleOwner = this


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_browse_title)
        return binding.root
    }



}