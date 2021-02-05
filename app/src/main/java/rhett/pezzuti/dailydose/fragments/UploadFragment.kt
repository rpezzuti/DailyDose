package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.TrackNotification
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.network.RetrofitInstance
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
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

    private val SENDER_ID = 792171633337

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

        val app = requireNotNull(this.activity).application

        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        binding.uploadViewModelXML = viewModel
        binding.lifecycleOwner = this


        /** FCM Notification Channel **/
        createChannel(
            getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name)
        )

        /** Local Notification Channel **/
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )


        // To make new topics, subscribe to them through here.
        viewModel.subscribeTopic(TOPIC_TEST)

        /** Upload Button Observer **/
        viewModel.eventUploadCheck.observe(viewLifecycleOwner, { event ->
            if (event == true) {

                /**
                TrackNotification(
                    NotificationData(
                        "https://www.youtube.com",
                        "dummy-title",
                        "dummy-artist",
                        "dummy-genre",
                        "dummy-image"
                    ), TOPIC_TEST
                ).also {
                    viewModel.sendNotification(it)
                }
                **/

                // sendNotificaitonWithIntent(databaseTrack, app)

                val fm = FirebaseMessaging.getInstance()

                val message = RemoteMessage.Builder("$SENDER_ID@fcm.googleapis.com")
                    .setMessageId(SENDER_ID.toString())
                    .addData("url", binding.etUploadLink.text.toString())
                    .addData("title", binding.etUploadTitle.text.toString())
                    .addData("artist", binding.etUploadArtist.text.toString())
                    .addData("genre", "DUMMYGENRE")
                    .addData("image", "DUMMYIMAGE")
                    .addData("timestamp", System.currentTimeMillis().toString())
                    .build()

                fm.send(message)


/*
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
*/

                viewModel.doneUploadCheck()
            }
        })




        fun uploadTrack() {
            val track = Track(
                binding.etUploadLink.text.toString(),
                binding.etUploadTitle.text.toString(),
                binding.etUploadArtist.text.toString(),
                "genre",
                "image",
                System.currentTimeMillis(),
                favorite = false
            )

            sendNotificaitonWithIntent(track, app)

        }

        return binding.root
    }

    fun sendNotification(notification: TrackNotification){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ${response.body().toString()}")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                    Log.e(TAG, "Failure Receiving response from RetrofitInstance")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
    }
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

    private fun sendNotificaitonWithIntent(track: Track, app: Application) {
        val notificationManager = ContextCompat.getSystemService(
            app.applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotificationWithIntent(track.title, track.artist, track.url, app.applicationContext)
    }
}