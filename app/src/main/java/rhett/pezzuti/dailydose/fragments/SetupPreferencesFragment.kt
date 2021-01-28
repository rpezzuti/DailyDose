package rhett.pezzuti.dailydose.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.activities.MainActivity
import rhett.pezzuti.dailydose.databinding.FragmentSetupPreferencesBinding
import rhett.pezzuti.dailydose.viewmodels.SetupPreferencesViewModel

class SetupPreferencesFragment : Fragment() {

    private lateinit var binding: FragmentSetupPreferencesBinding
    private lateinit var viewModel: SetupPreferencesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setup_preferences,
            container,
            false
        )


        val arguments = SetupPreferencesFragmentArgs.fromBundle(requireArguments())

        viewModel = ViewModelProvider(this).get(SetupPreferencesViewModel::class.java)
        binding.setupPreferencesViewModelXML = viewModel
        binding.lifecycleOwner = this


        binding.textPreferencesName.text = "What do you like, ${arguments.name}?"


        viewModel.donePreferences.observe(viewLifecycleOwner, { event ->
            if (event == true) {
                startMain()
                viewModel.doneFinishing()
            }
        })


        return binding.root
    }

    fun startMain() {
        val intent = Intent(this.activity, MainActivity::class.java)
        startActivity(intent)
    }


}