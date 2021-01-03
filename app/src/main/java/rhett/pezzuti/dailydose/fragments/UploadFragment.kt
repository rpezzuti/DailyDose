package rhett.pezzuti.dailydose.fragments

import android.app.AlertDialog
import android.content.DialogInterface
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

}