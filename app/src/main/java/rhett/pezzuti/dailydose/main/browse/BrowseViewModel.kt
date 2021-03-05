package rhett.pezzuti.dailydose.main.browse

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import rhett.pezzuti.dailydose.data.source.local.TrackDatabaseDao
import rhett.pezzuti.dailydose.data.Track
import rhett.pezzuti.dailydose.data.asDatabaseModel
import rhett.pezzuti.dailydose.network.BrowseFirebaseGson
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.local.getInstance
import rhett.pezzuti.dailydose.utils.asListOfTracks
import timber.log.Timber

enum class BrowseStatus { LOADING, ERROR, DONE}

class BrowseViewModel(
    val trackDatabase: TrackDatabaseDao,
    app: Application
) : AndroidViewModel(app) {

    private val _playlist = MutableLiveData<List<Track>>()
    val playlist: LiveData<List<Track>>
        get() = _playlist

    private val _status = MutableLiveData<BrowseStatus>()
    val status: LiveData<BrowseStatus>
        get() = _status

 /*   private val database = getInstance(getApplication())
    private val trackRepository = DefaultTrackRepository(database)*/

    /** Starts the whole shit of getting tracks from Firebase **/
    // private val _items = trackRepository.refreshTracks()


    init {
        getAllTracks()

        // Call adapter error
        //getAllTracksDeferred()
        viewModelScope.launch {
            /** Gets those two tracks from the test-genre-list and shows them in recycler view **/
            // defaultTrackRepository.refreshTestTracks()

            // defaultTrackRepository.getTracks()
        }
    }

    /** Observed playlist for the recycler View **/
   // val tracks = trackRepository.tracks

    private fun dummy1() {
        viewModelScope.launch {

        }
    }

    private fun getAllTracks() = viewModelScope.launch {

        _status.value = BrowseStatus.LOADING

        BrowseFirebaseGson.retrofitService.getAllTracks().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Timber.i("GREAT SUCCESS: ${response.body().toString()}")
                insertAll(response.body().asListOfTracks())
                _status.value = BrowseStatus.DONE
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Timber.i("Failure: " + t.message)
                _status.value = BrowseStatus.ERROR
            }
        })
    }

    private fun parseJsonAllTracks(data: JsonObject?) {

        // If somehow null, RV will show nothing but app will not crash. :)

        if (data == null) {
            // Do nothing
        } else {
            val keySet = data.keySet().toList()
            val jsonList = mutableListOf<JsonObject>()
            val trackList = mutableListOf<Track>()
            Timber.i("RHETT: List of data keys $keySet")

            // Make a list of JsonObjects, by genre
            for (keys in keySet) {
                Timber.i("RHETT: Genre Indicator $keys")
                Timber.i("RHETT: Data of that genre ${data[keys]}")

                jsonList.add(data.getAsJsonObject(keys))
                Timber.i("RHETT: Json List ${jsonList}")
            }


            // Parse each genre
            for (eachGenre in jsonList) {


                // Put the keys (track names) into a list
                val trackKeys = eachGenre.keySet().toList()

                // Initialize the list of tracks as Json objects
                val jsonObjects = mutableListOf<JsonObject?>()

                // Parse the Genre object into track sized objects
                // data.get() wants to return a JsonElement, so we cast it to JsonObject.
                // Lets us get variables in the next for loop
                for (i in trackKeys.indices) {
                    jsonObjects.add(i, eachGenre.get(trackKeys[i]).asJsonObject)
                }



                for (i in 0 until jsonObjects.size) {
                    val temp = Track(
                        jsonObjects[i]?.get("timestamp")!!.asLong,
                        jsonObjects[i]?.get("url").toString().removeSurrounding("\""),
                        jsonObjects[i]?.get("title").toString().removeSurrounding("\""),
                        jsonObjects[i]?.get("artist").toString().removeSurrounding("\""),
                        jsonObjects[i]?.get("genre").toString().removeSurrounding("\""),
                        jsonObjects[i]?.get("image").toString().removeSurrounding("\""),
                        jsonObjects[i]?.get("favorite")!!.asBoolean
                    )
                    trackList.add(temp)
                }
            }

            // Add the new tracklist to database
            insertAll(trackList.toList())
        }
    }

    // Doesn't work. Converter error attempting to get the networkTrackCOntainer
    private fun getAllTracksDeferred() = viewModelScope.launch {

        try {
            Timber.i("RHETT: TRY BLOCK ")
            val json = BrowseFirebaseGson.retrofitService.getAllTracksDeferred().await()
            Timber.i("RHETT: JSON OBJECT $json ")
            Timber.i("RHETT: JSON OBJECT as String ${json.toString()} ")
            parseJsonAllTracks(json.json)
        } catch (exception: HttpException) {
            Timber.i("Error motherfucker: $exception")
            Timber.i("Error motherfucker: ${exception.code()}")
            Timber.i("Error motherfucker: ${exception.message()}")
            Timber.i("Error motherfucker: ${exception.response().toString()}")
        }
    }




    /** Database Functions **/
    fun addToFavorites(timestamp: Long) {
        viewModelScope.launch {
            favorite(timestamp)
        }
    }

    private suspend fun favorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(timestamp)
            track.favorite = true
            trackDatabase.update(track)
        }
    }

    fun removeFromFavorites(timestamp: Long) {
        viewModelScope.launch {
            unFavorite(timestamp)
        }
    }

    private suspend fun unFavorite(timestamp: Long) {
        withContext(Dispatchers.IO) {
            val track = trackDatabase.getTrack(timestamp)
            track.favorite = false
            trackDatabase.update(track)
        }
    }

    private fun insertAll(trackList: List<Track>) {
        viewModelScope.launch {
            addNewTracks(trackList)
        }
    }

    private suspend fun addNewTracks(trackList: List<Track>){
        withContext(Dispatchers.IO) {
            val favorites = trackDatabase.getFavoritesToSave(true)
            if (favorites.isNullOrEmpty()) {
                trackDatabase.insertAll(*trackList.asDatabaseModel())
            } else {
                trackDatabase.insertAll(*trackList.asDatabaseModel())
                trackDatabase.insertAll(*favorites.toTypedArray())
            }
        }
    }

}