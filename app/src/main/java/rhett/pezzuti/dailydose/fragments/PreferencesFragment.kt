package rhett.pezzuti.dailydose.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentPreferencesBinding
import rhett.pezzuti.dailydose.viewmodels.PreferencesViewModel

class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding
    private lateinit var viewModel: PreferencesViewModel

    private val TOPIC_TEST = "Test"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_preferences,
            container,
            false
        )

        val app = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this).get(PreferencesViewModel::class.java)
        binding.preferencesViewModelXML = viewModel
        binding.lifecycleOwner = this


        viewModel.subscribeTopic(TOPIC_TEST)

        return binding.root
    }






}
