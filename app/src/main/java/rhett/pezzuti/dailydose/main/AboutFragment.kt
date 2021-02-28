package rhett.pezzuti.dailydose.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentAboutBinding
import rhett.pezzuti.dailydose.minigame.MinigameActivity
import rhett.pezzuti.dailydose.splash.SplashActivity


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about,
            container,
            false
        )

        binding.pictureOfMe.setOnClickListener {
            val intent = Intent(this.context, MinigameActivity::class.java)
            startActivity(intent)
        }


        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_about_title)
        return binding.root
    }
}