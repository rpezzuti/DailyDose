package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.domain.LocalTrack
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
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

        /** Returns null right now. No such track at path exists **/
        // getOneTrackFromFirebase()

        /** Displays a list of tracks from the test-genre-list child **/
        // getTestGenreListTrack()

        /** Displays the JSON object of the test-genre child as a String **/
        // getTestGenreJsonString()

        /** GETS one track from test-genre, parses it into a LocalTrack, and displays the title of that track **/
        // parseOneJson()

        /** Get a Json Object to be put into parser **/
        getJson()

        /** Gets those two tracks from the test-genre-list and shows them in recycler view **/
        viewModelScope.launch {
            trackRepository.refreshTestTracks()
        }
    }

    /** Observed playlist for the recycler View **/
    val testTracks = trackRepository.tracks


    private fun getOneTrackFromFirebase() {
        BrowseFirebaseMoshi.retrofitService.getOneTrackFromFirebase().enqueue(object :
            Callback<Track> {
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
        Timber.i("kek")
        BrowseFirebaseMoshi.retrofitService.getTestGenreListTrack().enqueue(object :
            Callback<List<Track>> {
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
        BrowseFirebaseScalars.retrofitService.getOneJsonString().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }



    private fun parseOneJson() {
        BrowseFirebaseMoshi.retrofitService.parseOneJson().enqueue(object : Callback<LocalTrack> {
            override fun onResponse(call: Call<LocalTrack>, response: Response<LocalTrack>) {
                _response.value = "Success: ${response.body()?.title.toString()} was received"
            }

            override fun onFailure(call: Call<LocalTrack>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }


    // Gson works
    // Now i have a Json object to play with =]
    private fun getJson(){
        BrowseFirebaseGson.retrofitService.getJsonObject().enqueue(object: Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                _response.value = "GREAT SUCCESS: ${response.body().toString()}"
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _response.value = "Failure: " + t.message
             }
        })
    }


    private fun parseJson(data: JSONObject?) {

        if (data != null) {

        }
    }


    private fun parseJson1(data: JSONObject?) {
        if (data != null) {
            val it = data.keys()
            while (it.hasNext()) {
                val key = it.next()
                try {
                    if (data[key] is JSONArray) {
                        val arry = data.getJSONArray(key)
                        val size = arry.length()
                        for (i in 0 until size) {
                            parseJson(arry.getJSONObject(i))
                        }
                    } else if (data[key] is JSONObject) {
                        parseJson(data.getJSONObject(key))
                    } else {
                        println(key + ":" + data.getString(key))
                    }
                } catch (e: Throwable) {
                    try {
                        println(key + ":" + data.getString(key))
                    } catch (ee: Exception) {
                    }
                    e.printStackTrace()
                }
            }
        }
    }







    private fun refreshTrackDatabase() = viewModelScope.launch {
        try {
            val playlist = BrowseFirebaseMoshi.retrofitService.refreshDatabase().await()
            _response.value = "it fucking worked"
            _playlist.postValue(playlist.asDomainModel())

        } catch (networkError: IOException) {
            _response.value = "Failure: " + networkError.message
        }
    }



    /** Database Functions **/
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

}