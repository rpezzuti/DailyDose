package rhett.pezzuti.dailydose.utils

import com.google.gson.JsonObject
import retrofit2.Call
import rhett.pezzuti.dailydose.data.DatabaseTrack
import rhett.pezzuti.dailydose.data.Track
import timber.log.Timber

fun JsonObject.asDatabaseModel(): Array<DatabaseTrack> {

    val trackList = mutableListOf<DatabaseTrack>()

    if (keySet() == null) {
        // Do nothing
    } else {
        val genres = keySet().toMutableList()

        if (genres.contains("Test")) {
            genres.remove("Test")
        }

        val jsonList = mutableListOf<JsonObject>()
        Timber.i("RHETT: List of data keys $genres")

        for (i in genres) {
            Timber.i("RHETT: Genre Indicator $i")

            jsonList.add(getAsJsonObject(i))
            Timber.i("RHETT: Json List ${jsonList}")
        }


        // Parse each genre
        for (yo in jsonList) {
            // Timber.i("RHETT: Loop of calling PraseJsonOneGenre${parseJsonOneGenre(yo)}")


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
                val temp = DatabaseTrack(
                    jsonObjects[i]?.get("timestamp")!!.asLong,
                    jsonObjects[i]?.get("url").toString(),
                    jsonObjects[i]?.get("title").toString(),
                    jsonObjects[i]?.get("artist").toString(),
                    jsonObjects[i]?.get("genre").toString(),
                    jsonObjects[i]?.get("image").toString(),
                    jsonObjects[i]?.get("favorite")!!.asBoolean
                )
                trackList.add(temp)
            }
        }

        // insertAll(trackList.toList())
    }

    return trackList.toTypedArray()
}

fun JsonObject?.asListOfTracks(): List<Track> {


    // If somehow null, RV will show nothing but app will not crash. :)

    if (this == null) {
        return emptyList()
    } else {
        val keySet = this.keySet().toMutableList()

        /** If there are items in the Test genre in Firebase, omit them **/
        if (keySet.contains("Test")) {
            keySet.remove("Test")
        }

        val jsonList = mutableListOf<JsonObject>()
        val trackList = mutableListOf<Track>()
        Timber.i("RHETT: List of data keys $keySet")

        // Make a list of JsonObjects, by genre
        for (keys in keySet) {
            Timber.i("RHETT: Genre Indicator $keys")
            Timber.i("RHETT: Data of that genre ${this[keys]}")

            jsonList.add(this.getAsJsonObject(keys))
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
        return trackList.toList()
    }



}
