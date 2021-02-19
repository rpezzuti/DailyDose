package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.TrackNotification
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.viewmodels.UploadViewModel

class UploadFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var viewModel: UploadViewModel
    private lateinit var TOPIC: String

    companion object {
        private const val TAG = "UploadFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upload,
            container,
            false
        )
        setupSpinner()

        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        binding.uploadViewModelXML = viewModel
        binding.lifecycleOwner = this


        /** Upload Button Observer **/
        viewModel.eventUploadCheck.observe(viewLifecycleOwner, { event ->
            if (event == true) {
                uploadAlert()
                viewModel.doneUploadCheck()
            }
        })


        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, { event ->
            if (event == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackBar()
            }
        })



        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_upload_title)
        return binding.root
    }

    private
    fun uploadAlert() {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.upload_alert_upload))
            .setMessage(getString(R.string.upload_alert_message))
            .setCancelable(false)

            .setPositiveButton(
                getString(R.string.upload_alert_positive_button),
                DialogInterface.OnClickListener { dialog, id ->
                    buildNotification()
                })

            .setNegativeButton(
                getString(R.string.upload_alert_negative_button),
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(context, "Didn't upload", Toast.LENGTH_SHORT).show()
                }
            )
            .create()
            .show()
    }

    private
    fun buildNotification() {
        TrackNotification(
            Track(
                binding.etUploadUrl.text.toString(),
                binding.etUploadTitle.text.toString(),
                binding.etUploadArtist.text.toString(),
                TOPIC,
                "dummy-image",
                System.currentTimeMillis(),
                false
            ), "/topics/$TOPIC"
        ).also {
            viewModel.sendNotification(it)
        }
    }

    private
    fun setupSpinner() {
        // Creating an Array Adapter using the string array and default spinner item layout.
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.genre_spinner,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            // Set the layout when the list of choices appears. Default Android again
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply adapter to Spinner View
            binding.uploadSpinner.adapter = arrayAdapter
        }
        binding.uploadSpinner.onItemSelectedListener = this
    }

    // Spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when (pos) {
            0 -> TOPIC = getString(R.string.TOPIC_TEST)
            1 -> TOPIC = getString(R.string.TOPIC_DUBSTEP)
            2 -> TOPIC = getString(R.string.TOPIC_MELODIC_DUBSTEP)
            3 -> TOPIC = getString(R.string.TOPIC_LO_FI)
            4 -> TOPIC = getString(R.string.TOPIC_CHILLSTEP)
            5 -> TOPIC = getString(R.string.TOPIC_FUTURE_GARAGE)
            6 -> TOPIC = getString(R.string.TOPIC_PIANO_AMBIENT)
            7 -> TOPIC = getString(R.string.TOPIC_EXPERIMENTAL_BASS)
            8 -> TOPIC = getString(R.string.TOPIC_LIQUID_DNB)
            9 -> TOPIC = getString(R.string.TOPIC_AMBIENT_BASS)

            10 -> TOPIC = getString(R.string.TOPIC_METALCORE)
            11 -> TOPIC = getString(R.string.TOPIC_ACOUSTIC_BALLADS)
            12 -> TOPIC = getString(R.string.TOPIC_INSTRUMENTAL_ROCK)
            13 -> TOPIC = getString(R.string.TOPIC_DEATH_METAL)
            14 -> TOPIC = getString(R.string.TOPIC_LIVE_PERFORMANCES)
            else -> {
                TOPIC = "dummy"
                Toast.makeText(this.requireContext(), "Hi Rhett", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Spinner
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TOPIC = getString(R.string.TOPIC_TEST)
        Toast.makeText(this.requireContext(), "Yo", Toast.LENGTH_SHORT).show()
    }

}