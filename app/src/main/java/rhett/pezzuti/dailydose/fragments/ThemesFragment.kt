package rhett.pezzuti.dailydose.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R

import rhett.pezzuti.dailydose.databinding.ThemesFragmentBinding
import rhett.pezzuti.dailydose.viewmodels.ThemesViewModel


class ThemesFragment : Fragment() {


    private lateinit var viewModel: ThemesViewModel
    private lateinit var binding: ThemesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.themes_fragment,
            container,
            false
        )

        val activity = requireActivity()






        binding.theme1.setOnClickListener {

            activity.setTheme(R.style.Theme_DailyDose)

        }

        binding.theme2.setOnClickListener {

            activity.setTheme(R.style.Theme_DailyDose_Variant)


        }


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_themes_title)
        return binding.root
    }
}