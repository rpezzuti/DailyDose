package rhett.pezzuti.dailydose.viewmodels

import android.app.Application
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessaging
import rhett.pezzuti.dailydose.activities.MainActivity
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.getInstance

class PreferencesViewModel(private val app: Application) : AndroidViewModel(app) {

    /** GENRES / TOPICS **/
    private val TOPIC_DUBSTEP = "dubstep"
    private val TOPIC_MELODIC_DUBSTEP = "melodic-dubstep"
    private val TOPIC_LO_FI = "lo-fi"
    private val TOPIC_CHILLSTEP = "chillstep"
    private val TOPIC_FUTURE_GARAGE = "future-garage"
    private val TOPIC_PIANO_AMBIENT = "piano-ambient"
    private val TOPIC_EXPERIMENTAL_BASS = "experimental-bass"
    private val TOPIC_LIQUID_DNB = "liquid-dnb"
    private val TOPIC_AMBIENT_BASS = "ambient-bass"

    private val TOPIC_METALCORE = "metalcore"
    private val TOPIC_ACOUSTIC_BALLADS = "acoustic-ballads"
    private val TOPIC_INSTRUMENTAL_ROCK = "instrumental-rock"
    private val TOPIC_DEATH_METAL = "death-metal"
    private val TOPIC_LIVE_PERFORMANCES = "live-performances"

    private val TOPIC_TEST = "Test"

    private val preferences = getInstance(app.applicationContext).userPreferencesDao

    /** Encapsulated Checkbox Triggers **/
    private val _checkBoxDubstep = MutableLiveData<Boolean>()
    val checkBoxDubstep : LiveData<Boolean>
        get() = _checkBoxDubstep

    private val _checkBoxMelodicDubstep = MutableLiveData<Boolean>()
    val checkBoxMelodicDubstep : LiveData<Boolean>
        get() = _checkBoxMelodicDubstep

    private val _checkBoxLoFi = MutableLiveData<Boolean>()
    val checkBoxLoFi : LiveData<Boolean>
        get() = _checkBoxLoFi

    private val _checkBoxChillstep = MutableLiveData<Boolean>()
    val checkBoxChillstep : LiveData<Boolean>
        get() = _checkBoxChillstep

    private val _checkBoxFutureGarage = MutableLiveData<Boolean>()
    val checkBoxFutureGarage : LiveData<Boolean>
        get() = _checkBoxFutureGarage

    private val _checkBoxPianoAmbient = MutableLiveData<Boolean>()
    val checkBoxPianoAmbient : LiveData<Boolean>
        get() = _checkBoxPianoAmbient

    private val _checkBoxExperimentalBass = MutableLiveData<Boolean>()
    val checkBoxExperimentalBass : LiveData<Boolean>
        get() = _checkBoxExperimentalBass

    private val _checkBoxLiquidDnB = MutableLiveData<Boolean>()
    val checkBoxLiquidDnB : LiveData<Boolean>
        get() = _checkBoxLiquidDnB

    private val _checkBoxAmbientBass = MutableLiveData<Boolean>()
    val checkBoxAmbientBass : LiveData<Boolean>
        get() = _checkBoxAmbientBass

    private val _checkBoxMetalcore = MutableLiveData<Boolean>()
    val checkBoxMetalcore : LiveData<Boolean>
        get() = _checkBoxMetalcore

    private val _checkBoxAcousticBallads = MutableLiveData<Boolean>()
    val checkBoxAcousticBallads : LiveData<Boolean>
        get() = _checkBoxAcousticBallads

    private val _checkBoxInstrumentalRock = MutableLiveData<Boolean>()
    val checkBoxInstrumentalRock : LiveData<Boolean>
        get() = _checkBoxInstrumentalRock

    private val _checkBoxDeathMetal = MutableLiveData<Boolean>()
    val checkBoxDeathMetal : LiveData<Boolean>
        get() = _checkBoxDeathMetal

    private val _checkBoxLivePerformances = MutableLiveData<Boolean>()
    val checkBoxLivePerformances : LiveData<Boolean>
        get() = _checkBoxLivePerformances

    init {

    }

    fun subTest() {
        subscribeTopic(TOPIC_TEST)
    }

    fun unsubTest() {
        unSubscribeTopic(TOPIC_TEST)
    }

    fun checkDubstep() {

        val checked = _checkBoxDubstep.value
        if (checked != true) {
            _checkBoxDubstep.value = true
            subscribeTopic(TOPIC_DUBSTEP)
        } else if (checked == true) {
            _checkBoxDubstep.value = false
            unSubscribeTopic(TOPIC_DUBSTEP)
        }
    }

    fun checkMelodicDubstep() {
        val checked = _checkBoxMelodicDubstep.value
        if (checked != true) {
            _checkBoxMelodicDubstep.value = true
            subscribeTopic(TOPIC_MELODIC_DUBSTEP)
        } else if (checked == true) {
            _checkBoxMelodicDubstep.value = false
            unSubscribeTopic(TOPIC_MELODIC_DUBSTEP)
        }
    }

    fun checkLoFi() {
        val checked = _checkBoxLoFi.value
        if (checked != true) {
            _checkBoxLoFi.value = true
            subscribeTopic(TOPIC_LO_FI)
        } else if (checked == true) {
            _checkBoxLoFi.value = false
            unSubscribeTopic(TOPIC_LO_FI)
        }
    }

    fun checkChillstep() {
        val checked = _checkBoxChillstep.value
        if (checked != true) {
            _checkBoxChillstep.value = true
            subscribeTopic(TOPIC_CHILLSTEP)
        } else if (checked == true) {
            _checkBoxChillstep.value = false
            unSubscribeTopic(TOPIC_CHILLSTEP)
        }
    }

    fun checkFutureGarage() {
        val checked = _checkBoxFutureGarage.value
        if (checked != true) {
            _checkBoxFutureGarage.value = true
            subscribeTopic(TOPIC_FUTURE_GARAGE)
        } else if (checked == true) {
            _checkBoxFutureGarage.value = false
            unSubscribeTopic(TOPIC_FUTURE_GARAGE)
        }
    }

    fun checkPianoAmbient() {
        val checked = _checkBoxPianoAmbient.value
        if (checked != true) {
            _checkBoxPianoAmbient.value = true
            subscribeTopic(TOPIC_PIANO_AMBIENT)
        } else if (checked == true) {
            _checkBoxPianoAmbient.value = false
            unSubscribeTopic(TOPIC_PIANO_AMBIENT)
        }
    }

    fun checkExperimentalBass() {
        val checked = _checkBoxExperimentalBass.value
        if (checked != true) {
            _checkBoxExperimentalBass.value = true
            subscribeTopic(TOPIC_EXPERIMENTAL_BASS)
        } else if (checked == true) {
            _checkBoxExperimentalBass.value = false
            unSubscribeTopic(TOPIC_EXPERIMENTAL_BASS)
        }
    }

    fun checkLiquidDnB() {
        val checked = _checkBoxLiquidDnB.value
        if (checked != true) {
            _checkBoxLiquidDnB.value = true
            subscribeTopic(TOPIC_LIQUID_DNB)
        } else if (checked == true) {
            _checkBoxLiquidDnB.value = false
            unSubscribeTopic(TOPIC_LIQUID_DNB)
        }
    }

    fun checkAmbientBass() {
        val checked = _checkBoxAmbientBass.value
        if (checked != true) {
            _checkBoxAmbientBass.value = true
            subscribeTopic(TOPIC_AMBIENT_BASS)
        } else if (checked == true) {
            _checkBoxAmbientBass.value = false
            unSubscribeTopic(TOPIC_AMBIENT_BASS)
        }
    }

    fun checkMetalcore() {
        val checked = _checkBoxMetalcore.value
        if (checked != true) {
            _checkBoxMetalcore.value = true
            subscribeTopic(TOPIC_METALCORE)
        } else if (checked == true) {
            _checkBoxMetalcore.value = false
            unSubscribeTopic(TOPIC_METALCORE)
        }
    }

    fun checkAcousticBallads() {
        val checked = _checkBoxAcousticBallads.value
        if (checked != true) {
            _checkBoxAcousticBallads.value = true
            subscribeTopic(TOPIC_ACOUSTIC_BALLADS)
        } else if (checked == true) {
            _checkBoxAcousticBallads.value = false
            unSubscribeTopic(TOPIC_ACOUSTIC_BALLADS)
        }
    }

    fun checkInstrumentalRock() {
        val checked = _checkBoxInstrumentalRock.value
        if (checked != true) {
            _checkBoxInstrumentalRock.value = true
            subscribeTopic(TOPIC_INSTRUMENTAL_ROCK)
        } else if (checked == true) {
            _checkBoxInstrumentalRock.value = false
            unSubscribeTopic(TOPIC_INSTRUMENTAL_ROCK)
        }
    }

    fun checkDeathMetal() {
        val checked = _checkBoxDeathMetal.value
        if (checked != true) {
            _checkBoxDeathMetal.value = true
            subscribeTopic(TOPIC_DEATH_METAL)
        } else if (checked == true) {
            _checkBoxDeathMetal.value = false
            unSubscribeTopic(TOPIC_DEATH_METAL)
        }
    }

    fun checkLivePerformances() {
        val checked = _checkBoxLivePerformances.value
        if (checked != true) {
            _checkBoxLivePerformances.value = true
            subscribeTopic(TOPIC_LIVE_PERFORMANCES)
        } else if (checked == true) {
            _checkBoxLivePerformances.value = false
            unSubscribeTopic(TOPIC_LIVE_PERFORMANCES)
        }
    }



    fun subscribe(view: CheckBox) {
        if (view.isChecked) {
            when (view.id) {
                R.id.checkbox_melodic_dubstep -> subscribeTopic("Melodic Dubstep")
                R.id.checkbox_ambient_bass -> subscribeTopic("Ambient Bass")
            }
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