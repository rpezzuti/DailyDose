package rhett.pezzuti.dailydose.setup.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import rhett.pezzuti.dailydose.R

class SetupPreferencesViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    private val _donePreferences = MutableLiveData<Boolean>()
    val donePreferences : LiveData<Boolean>
        get() = _donePreferences

    init {
        _donePreferences.value = false
    }

    fun finishedPreferences(){
        _donePreferences.value = true
    }

    fun doneFinishing() {
        _donePreferences.value = false
    }

    private val sharedPref: SharedPreferences? = app.getSharedPreferences(app.getString(R.string.user_preferences_key), Context.MODE_PRIVATE)

    // I think this is possible in some way???
    // Refer to the ToDo app. there is a view being passed in the XML for a checkbox.
    // Weird.
/*    fun checkBox(view: View) {
        when (view.id) {
            R.id.checkbox_acoustic_ballads -> someSubcribe()
        }
    }*/

    fun checkDubstep() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_DUBSTEP), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_DUBSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_DUBSTEP), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_DUBSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_DUBSTEP), true).apply()
        }
    }

    fun checkMelodicDubstep() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_MELODIC_DUBSTEP), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_MELODIC_DUBSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_MELODIC_DUBSTEP), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_MELODIC_DUBSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_MELODIC_DUBSTEP), true).apply()
        }
    }

    fun checkLoFi() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_LO_FI), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_LO_FI))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LO_FI), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_LO_FI))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LO_FI), true).apply()
        }
    }

    fun checkChillstep() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_CHILLSTEP), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_CHILLSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_CHILLSTEP), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_CHILLSTEP))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_CHILLSTEP), true).apply()
        }
    }

    fun checkGarage() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_GARAGE), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_GARAGE))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_GARAGE), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_GARAGE))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_GARAGE), true).apply()
        }
    }

    fun checkPianoAmbient() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_PIANO_AMBIENT), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_PIANO_AMBIENT))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_PIANO_AMBIENT), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_PIANO_AMBIENT))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_PIANO_AMBIENT), true).apply()
        }
    }

    fun checkExperimentalBass() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_EXPERIMENTAL_BASS), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_EXPERIMENTAL_BASS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_EXPERIMENTAL_BASS), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_EXPERIMENTAL_BASS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_EXPERIMENTAL_BASS), true).apply()
        }
    }

    fun checkLiquidDnB() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_LIQUID_DNB), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_LIQUID_DNB))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LIQUID_DNB), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_LIQUID_DNB))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LIQUID_DNB), true).apply()
        }
    }

    fun checkAmbientBass() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_AMBIENT_BASS), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_AMBIENT_BASS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_AMBIENT_BASS), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_AMBIENT_BASS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_AMBIENT_BASS), true).apply()
        }
    }

    fun checkMetalcore() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_METALCORE), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_METALCORE))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_METALCORE), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_METALCORE))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_METALCORE), true).apply()
        }
    }

    fun checkAcousticBallads() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_ACOUSTIC_BALLADS), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_ACOUSTIC_BALLADS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_ACOUSTIC_BALLADS), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_ACOUSTIC_BALLADS))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_ACOUSTIC_BALLADS), true).apply()
        }
    }

    fun checkInstrumentalRock() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_INSTRUMENTAL_ROCK), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_INSTRUMENTAL_ROCK))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_INSTRUMENTAL_ROCK), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_INSTRUMENTAL_ROCK))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_INSTRUMENTAL_ROCK), true).apply()
        }
    }

    fun checkDeathMetal() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_DEATH_METAL), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_DEATH_METAL))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_DEATH_METAL), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_DEATH_METAL))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_DEATH_METAL), true).apply()
        }
    }

    fun checkLivePerformances() {
        if (sharedPref!!.getBoolean(app.getString(R.string.TOPIC_LIVE_PERFORMANCES), false)) {
            unSubscribeTopic(app.getString(R.string.TOPIC_LIVE_PERFORMANCES))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LIVE_PERFORMANCES), false).apply()
        } else {
            subscribeTopic(app.getString(R.string.TOPIC_LIVE_PERFORMANCES))
            sharedPref.edit().putBoolean(app.getString(R.string.TOPIC_LIVE_PERFORMANCES), true).apply()
        }
    }


    fun subscribeTopic(genre: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(genre)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $genre"
                if (!task.isSuccessful) {
                    msg = app.resources.getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(app.applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private
    fun unSubscribeTopic(genre: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(genre)
            .addOnCompleteListener { task ->
                var msg = "Removed $genre from subscriptions"
                if (!task.isSuccessful) {
                    msg = app.resources.getString(R.string.message_unsubscribe_failed)
                }
                Toast.makeText(app.applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
    }
}