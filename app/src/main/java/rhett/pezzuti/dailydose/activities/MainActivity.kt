package rhett.pezzuti.dailydose.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rhett.pezzuti.dailydose.R

class MainActivity : AppCompatActivity() {

    companion object {
        val sharedPref: SharedPreferences? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Must be called before onCreate to reset the theme back to non-launcher
        setTheme(R.style.Theme_DailyDose)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}