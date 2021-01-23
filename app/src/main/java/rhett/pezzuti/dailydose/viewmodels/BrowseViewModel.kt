package rhett.pezzuti.dailydose.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import rhett.pezzuti.dailydose.network.FirebaseTrack
import timber.log.Timber
import java.util.*

class BrowseViewModel : ViewModel() {


    private val MELODIC_DUBSTEP = "/Melodic%20Dubstep/Drop%20Our%20Hearts%20Pt%20II"

    init {
        Timber.i("Hello World")
    }


    fun requestFromFirebase() {
        Timber.i("requestFromFirebase called")

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val reference = firebaseDatabase.getReference("testing/doubletest")

        reference.setValue("fuck")



        // Gives me https://daily-dose-f1709-default-rtdb.firebaseio.com/testing/doubletest with a value of "fuck"

        reference.addValueEventListener(object : ValueEventListener{
            @SuppressLint("LogNotTimber")
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                Log.i("BrowseViewModel","Here is the value: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.i("onCancelled called")
                Timber.i("Error: ${error.code}")
            }
        })

    }
}

/**
 *
 *
 *
 * val someData = ArrayList<FirebaseTrack>()
for (data in snapshot.children){
var model = data.getValue(FirebaseTrack::class.java)
someData.add(model as FirebaseTrack)
 *
 */