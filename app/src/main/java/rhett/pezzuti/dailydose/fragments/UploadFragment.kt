package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
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
import com.google.firebase.database.ktx.database
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.domain.NotificationData
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.TrackNotification
import rhett.pezzuti.dailydose.databinding.FragmentUploadBinding
import rhett.pezzuti.dailydose.network.FirebaseService
import rhett.pezzuti.dailydose.network.FirebaseTrack
import rhett.pezzuti.dailydose.network.MyFirebaseMessagingService
import rhett.pezzuti.dailydose.network.RetrofitInstance
import rhett.pezzuti.dailydose.utils.sendNotificationWithIntent
import rhett.pezzuti.dailydose.viewmodels.UploadViewModel
import timber.log.Timber

class UploadFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var viewModel: UploadViewModel

    private val SENDER_ID = 792171633337
    private val MESSAGE_ID = Math.random().toInt().toString()

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

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            MyFirebaseMessagingService.token = it.token
            etToken.setText(it.token)
        }


        val newT = FirebaseMessaging.getInstance().token.toString()
        binding.textFirebaseToken.text = newT

        /** Upload Button Observer **/
        viewModel.eventUploadCheck.observe(viewLifecycleOwner, { event ->
            if (event == true) {

                TrackNotification(
                    NotificationData(
                        "https://www.youtube.com",
                        "dummy-title",
                        "dummy-artist",
                        "dummy-genre",
                        "dummy-image"
                    ), "/topics/dubstep"
                ).also {
                    sendNotification(it)
                }



/*
                val newT = FirebaseInstanceId.getInstance().token

                val fm = FirebaseMessaging.getInstance()
                val message = RemoteMessage.Builder("$newT@fcm.googleapis.com")
                    .setMessageId(MESSAGE_ID)
                    .addData("url", binding.etUploadLink.text.toString())
                    .addData("title", binding.etUploadTitle.text.toString())
                    .addData("artist", binding.etUploadArtist.text.toString())
                    .addData("genre", "DUMMYGENRE")
                    .addData("image", "DUMMYIMAGE")
                    .addData("timestamp", System.currentTimeMillis().toString())
                    .build()
                fm.send(message)


*/


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




        return binding.root
    }


    /** Failed Retrofit Attempts **/
    private
    fun sendNotification(notification: TrackNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    // binding.retrofitResponse.text = "Success:${response.body().toString()}"
                    Timber.i("Success: ${response.body().toString()}")
                    Timber.i("Code: ${response.code()}")
                } else {
                    // binding.retrofitResponse.text = "Failure:${response.errorBody().toString()}"
                    Timber.i("Success: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                // binding.retrofitError.text = e.toString()
                Timber.i("Error: ${e.toString()}")
                Timber.i("Error: ${e.message}")
            }
        }
    }

    private
    fun saveTrackToFirebase(track: Track) {

        val firebaseDatabase = Firebase.database.reference
        val firebaseTrack = FirebaseTrack(track.title, track)
        firebaseDatabase.child("tracks").child(track.genre).setValue(firebaseTrack)
    }
}