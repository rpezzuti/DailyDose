package rhett.pezzuti.dailydose.viewmodels

import android.app.*
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.TrackNotification
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.network.FirebaseTrack
import rhett.pezzuti.dailydose.network.RetrofitInstance
import timber.log.Timber

class UploadViewModel(private val app: Application) : AndroidViewModel(app) {

    /** Event LiveData **/
    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    private val _eventUploadCheck = MutableLiveData<Boolean>()
    val eventUploadCheck: LiveData<Boolean>
        get() = _eventUploadCheck


    init {
        _eventUploadCheck.value = false
        _showSnackBarEvent.value = false
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            _showSnackBarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            val trackDatabase = getInstance(app.applicationContext).trackDatabaseDao
            trackDatabase.clearAll()
        }
    }

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }


    fun uploadCheck() {
        _eventUploadCheck.value = true
    }

    fun doneUploadCheck() {
        _eventUploadCheck.value = false
    }



    fun sendNotification(notification: TrackNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    // binding.retrofitResponse.text = "Success:${response.body().toString()}"
                    Timber.i("Success: ${response.body().toString()}")
                    Timber.i("Code: ${response.code()}")
                    saveTrackToFirebase(notification.data)
                    // firebaseUploadTesting(notification.data)
                } else {
                    // binding.retrofitResponse.text = "Failure:${response.errorBody().toString()}"
                    Timber.i("Error: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                // binding.retrofitError.text = e.toString()
                Timber.i("Error: ${e.toString()}")
                Timber.i("Error: ${e.message}")
            }
        }
    }


    private suspend fun saveTrackToFirebase(track: Track) {
        val firebaseDatabase = Firebase.database.reference
        firebaseDatabase.child("tracks").child(track.genre).child(track.title).setValue(track)
    }

    private suspend fun firebaseUploadTesting(track: Track) {
        val firebaseDatabase = Firebase.database.reference
        val firebaseTrack = FirebaseTrack(track.title, track)
        val track1 = Track(
            "https://soundcloud.com/nurkomusic/nurko-feat-rory-better-off-lonely-1",
            "Better Off Lonely",
            "Nurko",
            "Melodic Dubstep",
            "dummy-image",
            System.currentTimeMillis(),
            false
        )
        val track2 = Track(
            "https://soundcloud.com/neuromask/clockvice-it-sounds-like-were-breaking",
            "It Sounds Like We're Breaking",
            "Clockvice",
            "Melodic Dubstep",
            "dummy-image",
            System.currentTimeMillis(),
            false
        )
        firebaseDatabase.child("tracks").child("test-genre-list").setValue(listOf(track1, track2))

        /** WHAT I WANT **/
        firebaseDatabase.child("tracks").child("test-genre").child(track1.title).setValue(track1)
        firebaseDatabase.child("tracks").child("test-genre").child(track2.title).setValue(track2)

    }

}

