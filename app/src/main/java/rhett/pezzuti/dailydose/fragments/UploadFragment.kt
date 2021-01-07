package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.utils.sendNotification
import rhett.pezzuti.dailydose.viewmodels.UploadViewModel
import timber.log.Timber

class UploadFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var viewModel: UploadViewModel

    private val TOPIC = "dubstep"

    companion object {
        private const val TAG = "UploadFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.i("init token")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upload,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        binding.uploadViewModelXML = viewModel
        binding.lifecycleOwner = this


        createChannel(
            getString(R.string.fcm_test_notification_channel_id),
            getString(R.string.fcm_test_notification_channel_name)
        )

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )



        subscribeTopic()
        binding.buttonUpload.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .setTitle("UPLOAD?!")
                .setMessage("Are you sure you want to upload?")

            // When you click outside, it will not close.
            builder.setCancelable(false)

            builder.setPositiveButton(
                "Yes, Upload",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(context, "Uplaoded", Toast.LENGTH_SHORT).show()
                }
            )

            builder.setNegativeButton(
                "No, wait",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(context, "Didn't upload", Toast.LENGTH_SHORT).show()
                }
            )

            builder.create()

            builder.show()
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
    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var msg = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
    }

}