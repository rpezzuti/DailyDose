package rhett.pezzuti.dailydose.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentPreferencesBinding
import rhett.pezzuti.dailydose.factory.PreferencesViewModelFactory
import rhett.pezzuti.dailydose.viewmodels.PreferencesViewModel

class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding
    private lateinit var viewModel: PreferencesViewModel
    private lateinit var viewModelFactory: PreferencesViewModelFactory

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

        val highScore: Int = 0
        val username: String = "Rhett"

        val app = requireNotNull(this.activity).application

        viewModelFactory = PreferencesViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PreferencesViewModel::class.java)
        binding.preferencesViewModelXML = viewModel
        binding.lifecycleOwner = this
        viewModel.subscribeTopic(TOPIC_TEST)

        initializeBoxes(binding)
        return binding.root
    }



    private fun initializeBoxes(binding: FragmentPreferencesBinding) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defaultToNotChecked = 0

        if (sharedPref?.getInt("dubstep", defaultToNotChecked) == 1){
            binding.checkboxDubstep.isChecked = true
        }



    }




}
