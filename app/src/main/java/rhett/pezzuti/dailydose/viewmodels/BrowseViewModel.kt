package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.network.BrowseFirebaseMoshi
import rhett.pezzuti.dailydose.network.BrowseFirebaseScalars
import rhett.pezzuti.dailydose.network.asDomainModel
import rhett.pezzuti.dailydose.repository.TrackRepository
import timber.log.Timber
import java.io.IOException

class BrowseViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private val _playlist = MutableLiveData<List<Track>?>()
    val playlist : LiveData<List<Track>?>
        get() = _playlist

    private val database = getInstance(getApplication())
    private val trackRepository = TrackRepository(database)


    init {
        // _response.value = "broken AF"
        // getOneTrackFromFirebase()
        // getTestGenreListTrack()
        getTestGenreJsonString()


        viewModelScope.launch {
            trackRepository.refreshTestTracks()
        }
    }

    val testTracks = trackRepository.tracks


    private fun refreshTrackDatabase() = viewModelScope.launch {
        try {
            val playlist = BrowseFirebaseMoshi.retrofitService.refreshDatabase().await()
            _response.value = "it fucking worked"
            _playlist.postValue(playlist.asDomainModel())

        } catch (networkError: IOException) {
            _response.value = "Failure: " + networkError.message
        }
    }




    private fun getOneTrackFromFirebase() {
        BrowseFirebaseMoshi.retrofitService.getOneTrackFromFirebase().enqueue( object: Callback<Track>{
            override fun onResponse(call: Call<Track>, response: Response<Track>) {
                _response.value = response.body().toString()
            }

            override fun onFailure(call: Call<Track>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }

    // Moshi Works
    // Scalars returns a converter error
    private fun getTestGenreListTrack() {
        Timber.i("FUCK")
        BrowseFirebaseMoshi.retrofitService.getTestGenreListTrack().enqueue( object: Callback<List<Track>>{
            override fun onResponse(call: Call<List<Track>>, response: Response<List<Track>>) {
                _response.value = response.body().toString()
                _playlist.value = response.body()
            }
            override fun onFailure(call: Call<List<Track>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }

    // Scalars Works
    // Moshi returns "Expected a string but was BEGIN_OBJECT at path $"
    private fun getTestGenreJsonString() {
        BrowseFirebaseScalars.retrofitService.getOneJsonString().enqueue( object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body().toString()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }

    fun addToFavorites(url: String) {
        viewModelScope.launch {
            favorite(url)
        }
    }

    private suspend fun favorite(url: String) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(url)
            track.favorite = true
            trackDatabase.update(track)
        }
    }

    fun removeFromFavorites(url: String) {
        viewModelScope.launch {
            unFavorite(url)
        }
    }

    private suspend fun unFavorite(url: String) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(url)
            track.favorite = false
            trackDatabase.update(track)
        }
    }


    /**
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
    **/
}