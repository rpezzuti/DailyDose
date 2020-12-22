package rhett.pezzuti.dailydose

import android.app.Application
import timber.log.Timber

class DailyDoseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}