package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rhett.pezzuti.dailydose.database.TrackDatabaseDao
import rhett.pezzuti.dailydose.database.domain.LocalTrack
import rhett.pezzuti.dailydose.database.domain.Track
import rhett.pezzuti.dailydose.database.domain.asDatabaseModel
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.network.BrowseFirebaseMoshi
import rhett.pezzuti.dailydose.network.BrowseFirebaseScalars
import rhett.pezzuti.dailydose.repository.TrackRepository
import timber.log.Timber


class BrowseViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {


    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _playlist = MutableLiveData<List<Track>>()
    val playlist: LiveData<List<Track>>
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


        viewModelScope.launch {
            /** Gets those two tracks from the test-genre-list and shows them in recycler view **/
            // trackRepository.refreshTestTracks()

            /** Get a Json Object to be put into parser **/
            // getJsonOneGenre()
            getAllTracks()

            // trackRepository.getTracks()
        }
    }

    /** Observed playlist for the recycler View **/
    val tracks = trackRepository.tracks


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
    // If the Genre is empty, I just get a response onFailure error. The app does not crash and the RV
    // just shows null. so theoretically, having nothing in a genre is okay :)
    private fun getJsonOneGenre() {

        BrowseFirebaseGson.retrofitService.getMelodicDubstep()
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    _response.value = "GREAT SUCCESS: ${response.body().toString()}"
                    parseJsonOneGenre(response.body())
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })


    }

    private fun parseJsonOneGenre(data: JsonObject?) {

        if (data == null) {
            // Do Nothing
        } else if (data.size() == 1) {
            val key = data.keySet().toList()
            val jsonObject = data.get(key[0]).asJsonObject
            val tempTrack = Track(
                jsonObject.get("url").toString(),
                jsonObject.get("title").toString(),
                jsonObject.get("artist").toString(),
                jsonObject.get("genre").toString(),
                jsonObject.get("image").toString(),
                jsonObject.get("timestamp")!!.asLong,
                jsonObject.get("favorite")!!.asBoolean
            )
            _playlist.value = listOf(tempTrack)
        } else {
            // Put the keys (track names) into a list
            val keys = data.keySet().toList()

            // Initialize the list of tracks as Json objects
            val jsonObjects = mutableListOf<JsonObject?>()

            // Parse the Genre object into track sized objects
            // data.get() wants to return a JsonElement, so we cast it to JsonObject.
            // Lets us get variables in the next for loop
            for (i in keys.indices) {
                jsonObjects.add(i, data.get(keys[i]).asJsonObject)
            }


            val masterList = mutableListOf<Track>()

            for (i in 0 until jsonObjects.size) {
                val temp = Track(
                    jsonObjects[i]?.get("url").toString(),
                    jsonObjects[i]?.get("title").toString(),
                    jsonObjects[i]?.get("artist").toString(),
                    jsonObjects[i]?.get("genre").toString(),
                    jsonObjects[i]?.get("image").toString(),
                    jsonObjects[i]?.get("timestamp")!!.asLong,
                    jsonObjects[i]?.get("favorite")!!.asBoolean
                )
                masterList.add(temp)
            }

            _playlist.value = masterList.toList()
        }
    }

    private fun getAllTracks() {

        BrowseFirebaseGson.retrofitService.getAllTracks().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                _response.value = "GREAT SUCCESS: ${response.body().toString()}"
                parseJsonAllTracks(response.body())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        })
    }

    private fun parseJsonAllTracks(data: JsonObject?) {

        if (data == null) {
            // Do nothing
        } else {
            val genres = data.keySet().toList()
            val jsonList = mutableListOf<JsonObject>()
            val trackList = mutableListOf<Track>()
            Timber.i("RHETT: List of data keys $genres")

            for (i in genres) {
                Timber.i("RHETT: Genre Indicator $i")
                Timber.i("RHETT: Data of that genre ${data[i]}")

                jsonList.add(data.getAsJsonObject(i))
                Timber.i("RHETT: Json List ${jsonList}")
            }


            // Parse each genre
            for (yo in jsonList) {
                Timber.i("RHETT: Loop of calling PraseJsonOneGenre${parseJsonOneGenre(yo)}")


                // Put the keys (track names) into a list
                val keys = yo.keySet().toList()

                // Initialize the list of tracks as Json objects
                val jsonObjects = mutableListOf<JsonObject?>()

                // Parse the Genre object into track sized objects
                // data.get() wants to return a JsonElement, so we cast it to JsonObject.
                // Lets us get variables in the next for loop
                for (i in keys.indices) {
                    jsonObjects.add(i, yo.get(keys[i]).asJsonObject)
                }



                for (i in 0 until jsonObjects.size) {
                    val temp = Track(
                        jsonObjects[i]?.get("url").toString(),
                        jsonObjects[i]?.get("title").toString(),
                        jsonObjects[i]?.get("artist").toString(),
                        jsonObjects[i]?.get("genre").toString(),
                        jsonObjects[i]?.get("image").toString(),
                        jsonObjects[i]?.get("timestamp")!!.asLong,
                        jsonObjects[i]?.get("favorite")!!.asBoolean
                    )
                    trackList.add(temp)
                }
            }

            insertAll(trackList.toList())
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

    private fun insertAll(trackList: List<Track>) {
        viewModelScope.launch {
            insertAllNow(trackList)
        }
    }

    private suspend fun insertAllNow(trackList: List<Track>){
        withContext(Dispatchers.IO) {
            val favorites = trackDatabase.saveFavorites(true)
            if (favorites.isNullOrEmpty()) {
                trackDatabase.insertAll(*trackList.asDatabaseModel())
            } else {
                trackDatabase.insertAll(*trackList.asDatabaseModel())
                trackDatabase.insertAll(*favorites.toTypedArray())
            }
        }
    }



}