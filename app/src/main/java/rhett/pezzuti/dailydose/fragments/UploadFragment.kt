package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.utils.sendNotification
import rhett.pezzuti.dailydose.viewmodels.UploadViewModel

class UploadFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var viewModel: UploadViewModel

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

        binding.buttonUpload.setOnClickListener {

            createChannel(
                getString(R.string.notification_channel_id),
                getString(R.string.notification_channel_name)
            )

            createChannel(
                getString(R.string.fcm_test_notification_channel_id),
                getString(R.string.fcm_test_notification_channel_name)
            )
        }


        return binding.root
    }

    // Creates notification Channel
    private fun createChannel(channelId: String, channelName: String) {

        // API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}