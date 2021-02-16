package rhett.pezzuti.dailydose.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref = getSharedPreferences(getString(R.string.user_preferences_key), Context.MODE_PRIVATE)

        CoroutineScope(Dispatchers.IO).launch {

            if (sharedPref.getString("username", null) == null) {
                startSetup()
                // Prevent Backwards Navigation
                finish()
            } else {
                startMain()
                // Prevent Backwards Navigation
                finish()
            }
        }

    }

    private fun startMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun startSetup(){
        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}