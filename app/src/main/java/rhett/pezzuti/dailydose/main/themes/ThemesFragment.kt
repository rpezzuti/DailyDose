package rhett.pezzuti.dailydose.main.themes


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentThemesBinding
import rhett.pezzuti.dailydose.main.themes.ThemesViewModel


class ThemesFragment : Fragment() {


    private lateinit var viewModel: ThemesViewModel
    private lateinit var binding: FragmentThemesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_themes,
            container,
            false
        )






        binding.theme1.setOnClickListener {

            activity?.setTheme(R.style.Theme_DailyDose)

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.edit()?.putInt(getString(R.string.main_activity_theme_key), 0)?.apply()

            activity?.recreate()

        }

        binding.theme2.setOnClickListener {

            activity?.setTheme(R.style.Theme_DailyDose_Variant)

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.edit()?.putInt(getString(R.string.main_activity_theme_key), 1)?.apply()

            activity?.recreate()
        }

        binding.theme3.setOnClickListener {

            activity?.setTheme(R.style.Theme_DailyDose_Third)

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.edit()?.putInt(getString(R.string.main_activity_theme_key), 2)?.apply()

            activity?.recreate()
        }


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_themes_title)
        return binding.root
    }
}