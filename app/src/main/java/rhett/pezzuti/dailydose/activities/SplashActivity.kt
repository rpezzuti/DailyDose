package rhett.pezzuti.dailydose.activities

import android.content.Intent
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

        val app = requireNotNull(this).application

        CoroutineScope(Dispatchers.IO).launch {
            val userDatabase = getInstance(app.applicationContext).userPreferencesDao

            if (userDatabase.isUserInitialized() == null) {
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