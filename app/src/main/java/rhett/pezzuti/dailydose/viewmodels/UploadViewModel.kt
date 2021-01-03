package rhett.pezzuti.dailydose.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

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
}