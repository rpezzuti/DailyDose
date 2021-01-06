package rhett.pezzuti.dailydose.viewmodels

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.receiver.AlarmReceiver
import rhett.pezzuti.dailydose.utils.sendNotification

class UploadViewModel(private val app: Application) : AndroidViewModel(app) {



    init {

    }


    // Doesn't work. Prolly has to do with the context.
    fun uploadCheck() {
        val builder = AlertDialog.Builder(app.applicationContext)
            .setTitle("UPLOAD?!")
            .setMessage("Are you sure you want to upload?")

        // When you click outside, it will not close.
        builder.setCancelable(false)

        builder.setPositiveButton(
          "Yes, Upload",
            DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(app, "Uplaoded", Toast.LENGTH_SHORT).show()
            }
        )

        builder.setNegativeButton(
            "No, wait",
            DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(app, "Didn't upload", Toast.LENGTH_SHORT).show()
            }
        )

        builder.create()

        builder.show()
    }

    fun sendNotification() {

        val notificationManager = ContextCompat.getSystemService(
            app.applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            "text",
            app.applicationContext
        )
    }
}

//val builder = AlertDialog.Builder(context)
//    .setTitle("UPLOAD?!")
//    .setMessage("Are you sure you want to upload?")
//
//// When you click outside, it will not close.
//builder.setCancelable(false)
//
//builder.setPositiveButton(
//"Yes, Upload",
//DialogInterface.OnClickListener { dialog, id ->
////                    Toast.makeText(context, "Uplaoded", Toast.LENGTH_SHORT).show()
//    viewModel.sendNotification()
//}
//)
//
//builder.setNegativeButton(
//"No, wait",
//DialogInterface.OnClickListener { dialog, id ->
//    Toast.makeText(context, "Didn't upload", Toast.LENGTH_SHORT).show()
//}
//)
//builder.create()
//builder.show()