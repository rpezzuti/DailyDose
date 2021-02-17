package rhett.pezzuti.dailydose.utils

import com.google.gson.JsonObject
import rhett.pezzuti.dailydose.database.DatabaseTrack
import timber.log.Timber

fun JsonObject.asDatabaseModel(): Array<DatabaseTrack> {

    val trackList = mutableListOf<DatabaseTrack>()

    if (keySet() == null) {
        // Do nothing
    } else {
        val genres = keySet().toList()
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

        // insertAll(trackList.toList())
    }

    return trackList.toTypedArray()
}
