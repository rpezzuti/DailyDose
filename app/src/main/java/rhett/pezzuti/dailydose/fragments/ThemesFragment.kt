package rhett.pezzuti.dailydose.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import rhett.pezzuti.dailydose.R


class ThemesFragment : Fragment() {


    private lateinit var viewModel: ThemesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {












        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_themes_title)
        return inflater.inflate(R.layout.themes_fragment, container, false)
    }
}