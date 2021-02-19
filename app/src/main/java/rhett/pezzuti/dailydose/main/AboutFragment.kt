package rhett.pezzuti.dailydose.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import rhett.pezzuti.dailydose.R


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_about_title)
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}