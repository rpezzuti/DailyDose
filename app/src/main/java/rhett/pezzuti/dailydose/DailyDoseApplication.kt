package rhett.pezzuti.dailydose

import android.app.Application
import rhett.pezzuti.dailydose.database.getInstance
import timber.log.Timber

class DailyDoseApplication: Application() {

    companion object {

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // Put all database stuff in this layer
       getInstance(applicationContext)
    }





}

