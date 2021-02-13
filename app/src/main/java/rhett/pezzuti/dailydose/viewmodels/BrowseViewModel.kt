package rhett.pezzuti.dailydose.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.network.BrowseFirebaseApi
import rhett.pezzuti.dailydose.network.NetworkTrackContainer
import rhett.pezzuti.dailydose.network.asDomainModel
import timber.log.Timber
import java.io.IOException

class BrowseViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {


    private val MELODIC_DUBSTEP = "/Melodic%20Dubstep/Drop%20Our%20Hearts%20Pt%20II"

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private val _playlist = MutableLiveData<List<Track>?>()
    val playlist : LiveData<List<Track>?>
        get() = _playlist



    init {
        // _response.value = "broken AF"
        // getOneTrackFromFirebase()
        getOneGenreFromFirebase()
    }

    fun getStuff() {

        val aString: String = ""
        val reader = JSONObject(aString)

        val sys : JSONObject = reader.getJSONObject("sys")
        val country = sys.getString("country")

        

    }


    private fun refreshTrackDatabase() = viewModelScope.launch {
        try {
            val playlist = BrowseFirebaseApi.retrofitService.refreshDatabase().await()
            _response.value = "it fucking worked"
            _playlist.postValue(playlist.asDomainModel())

        } catch (networkError: IOException) {
            _response.value = "Failure: " + networkError.message
        }
    }


    private fun getOneTrackFromFirebase() {
        BrowseFirebaseApi.retrofitService.getOneTrackFromFirebase().enqueue( object: Callback<Track>{
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                _response.value = response.body().toString()
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }

    private fun getOneGenreFromFirebase() {
        BrowseFirebaseApi.retrofitService.getOneGenreFromFirebase().enqueue( object: Callback<List<Track>>{
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                _response.value = response.body().toString()
                _playlist.value = response.body()
            }
            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                _response.value = "Failure: " + t.message
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