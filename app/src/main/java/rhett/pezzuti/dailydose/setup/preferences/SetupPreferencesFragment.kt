package rhett.pezzuti.dailydose.setup.preferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.main.MainActivity
import rhett.pezzuti.dailydose.databinding.FragmentSetupPreferencesBinding

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

        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.user_preferences_key), Context.MODE_PRIVATE)
        val username = sharedPref.getString(getString(R.string.sharedpref_username_key), "bob")

        viewModel = ViewModelProvider(this).get(SetupPreferencesViewModel::class.java)
        binding.setupPreferencesViewModelXML = viewModel
        binding.lifecycleOwner = this


        binding.textPreferencesName.text = "What do you like, $username?"


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