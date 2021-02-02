package rhett.pezzuti.dailydose.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.database.domain.LocalTrack
import rhett.pezzuti.dailydose.network.BrowseFirebaseApi
import timber.log.Timber

class BrowseViewModel : ViewModel() {


    private val MELODIC_DUBSTEP = "/Melodic%20Dubstep/Drop%20Our%20Hearts%20Pt%20II"

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    init {
        getTracksFromFirebase()
    }

    fun getStuff() {

        val aString: String = ""
        val reader = JSONObject(aString)

        val sys : JSONObject = reader.getJSONObject("sys")
        val country = sys.getString("country")

        

    }




    private fun getTracksFromFirebase() {
        BrowseFirebaseApi.retrofitService.getAllTracks().enqueue( object: Callback<List<LocalTrack>>{
            override fun onFailure(call: Call<List<LocalTrack>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<List<LocalTrack>>, response: Response<List<LocalTrack>>) {
                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
            }
        })
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