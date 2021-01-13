package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.viewmodels.UploadViewModel
import timber.log.Timber

class UploadFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var viewModel: UploadViewModel

    private val TOPIC_DUBSTEP = "dubstep"
    private val TOPIC_MELODIC_DUBSTEP = "melodic-dubstep"
    private val TOPIC_LO_FI = "lo-fi"
    private val TOPIC_CHILLSTEP = "chillstep"
    private val TOPIC_GARAGE = "garage"
    private val TOPIC_PIANO_AMBIENT = "piano-ambient"
    private val TOPIC_TEST = "Test"

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

        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        binding.uploadViewModelXML = viewModel
        binding.lifecycleOwner = this


        // FCM Channel
        createChannel(
            getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name)
        )

        // Local Channel
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )



        // To make new topics, subscribe to them through here.
        viewModel.subscribeTopic(TOPIC_TEST)

        /** Upload Button Observer **/
        viewModel.eventUploadCheck.observe(viewLifecycleOwner, { event ->
            if (event == true) {
                val builder = AlertDialog.Builder(context)
                    .setTitle("Upload?")
                    .setMessage("Are you sure you want to upload?")
                    .setCancelable(false)

                    .setPositiveButton(
                        "Yes, Upload",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show()
                    })

                    .setNegativeButton(
                        "No, wait",
                        DialogInterface.OnClickListener { dialog, id ->
                            Toast.makeText(context, "Didn't upload", Toast.LENGTH_SHORT).show()
                        }
                    )
                    .create()
                    .show()

                viewModel.doneUploadCheck()
            }
        })

        return binding.root
    }

    // Creates notification Channel
    private fun createChannel(channelId: String, channelName: String) {

        // API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Music's pretty cool"

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}