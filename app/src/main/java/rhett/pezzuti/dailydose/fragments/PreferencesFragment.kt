package rhett.pezzuti.dailydose.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentPreferencesBinding
import rhett.pezzuti.dailydose.factory.PreferencesViewModelFactory
import rhett.pezzuti.dailydose.viewmodels.PreferencesViewModel

class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding
    private lateinit var viewModel: PreferencesViewModel
    private lateinit var viewModelFactory: PreferencesViewModelFactory

    // Called after onCreateView() returns
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireNotNull(this.activity).application
        val preferences = getInstance(app.applicationContext).userPreferencesDao

    }

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

        viewModelFactory = PreferencesViewModelFactory(app, binding)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PreferencesViewModel::class.java)
        binding.preferencesViewModelXML = viewModel
        binding.lifecycleOwner = this
        setupBoxes()






        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_preferences_title)
        return binding.root
    }

    private
    fun setupBoxes() {

        val sharedPref = activity?.getSharedPreferences(getString(R.string.user_preferences_key), Context.MODE_PRIVATE)

        if (sharedPref!!.getBoolean(getString(R.string.TOPIC_DUBSTEP), false)) {
            binding.checkboxDubstep.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_MELODIC_DUBSTEP), false)) {
            binding.checkboxMelodicDubstep.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_MELODIC_DUBSTEP), false)) {
            binding.checkboxMelodicDubstep.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_LO_FI), false)) {
            binding.checkboxLoFi.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_CHILLSTEP), false)) {
            binding.checkboxChillstep.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_FUTURE_GARAGE), false)) {
            binding.checkboxFutureGarage.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_PIANO_AMBIENT), false)) {
            binding.checkboxPianoAmbient.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_EXPERIMENTAL_BASS), false)) {
            binding.checkboxExperimentalBass.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_LIQUID_DNB), false)) {
            binding.checkboxLiquidDnb.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_AMBIENT_BASS), false)) {
            binding.checkboxAmbientBass.isChecked = true
        }


        if (sharedPref.getBoolean(getString(R.string.TOPIC_METALCORE), false)) {
            binding.checkboxMetalcore.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_ACOUSTIC_BALLADS), false)) {
            binding.checkboxAcousticBallads.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_INSTRUMENTAL_ROCK), false)) {
            binding.checkboxInstrumentalRock.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_DEATH_METAL), false)) {
            binding.checkboxDeathMetal.isChecked = true
        }
        if (sharedPref.getBoolean(getString(R.string.TOPIC_LIVE_PERFORMANCES), false)) {
            binding.checkboxLivePerformances.isChecked = true
        }

    }



}
