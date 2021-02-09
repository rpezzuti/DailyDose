package rhett.pezzuti.dailydose.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.FragmentSetupNameBinding
import rhett.pezzuti.dailydose.factory.SetupNameViewModelFactory
import rhett.pezzuti.dailydose.viewmodels.SetupNameViewModel


class SetupNameFragment : Fragment() {


    private lateinit var binding: FragmentSetupNameBinding
    private lateinit var viewModel: SetupNameViewModel
    private lateinit var viewModelFactory: SetupNameViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_setup_name,
            container,
            false
        )

        val app = requireNotNull(this.activity).application
        val userDatabase = getInstance(app.applicationContext).userPreferencesDao

        viewModelFactory = SetupNameViewModelFactory(userDatabase, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SetupNameViewModel::class.java)
        binding.setupNameViewModelXML = viewModel
        binding.lifecycleOwner = this

        /** TextView Animations **/
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1500L

        val fadeOut: Animation = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 1500L

        val fadeInOut = AnimationSet(true)
        fadeInOut.addAnimation(fadeIn)
        fadeInOut.addAnimation(fadeOut)

        // binding.textSetupHello.startAnimation(fadeInOut)







        viewModel.navigatePreferences.observe(viewLifecycleOwner, { event ->
            if (event == true) {
                if (binding.editSetupName.text?.isNotEmpty() == true){

                    viewModel.createNewUser(binding.editSetupName.text.toString())

                    this.findNavController().navigate(SetupNameFragmentDirections.actionSetupNameFragmentToSetupPreferencesFragment(binding.editSetupName.text.toString()))
                } else {
                    Toast.makeText(context, "Enter your name!", Toast.LENGTH_SHORT).show()
                    binding.setupInputLayout.error = "Please enter your name"
                }
                viewModel.doneNavigatingPreferences()
            }
        })

        return binding.root
    }

}