package rhett.pezzuti.dailydose.main.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import rhett.pezzuti.dailydose.R


class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_info_title)
        return inflater.inflate(R.layout.fragment_info, container, false)
    }
}