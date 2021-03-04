package rhett.pezzuti.dailydose

import android.app.Application
import android.app.Service
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rhett.pezzuti.dailydose.data.source.TrackRepository
import rhett.pezzuti.dailydose.work.RefreshDataWorker
import rhett.pezzuti.dailydose.work.UploadPreferencesWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class DailyDoseApplication : Application() {

    val trackRepository : TrackRepository
        get() = ServiceLocator.provideTrackRepository(this)

    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */

    /**
     * the delayedInit() design pattern is used to throw any app initialization on background thread,
     * thus letting the user see the first screen while the rest of the app loads in the background.
     * Very common and useful design pattern.
     */

    private val applicationScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // delayedInit()
        anotherDelayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun anotherDelayedInit() = applicationScope.launch {
        setupRecurringUploadSharedPref()
    }

    private fun setupRecurringUploadSharedPref() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()


        val repeatingRequest = PeriodicWorkRequestBuilder<UploadPreferencesWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            UploadPreferencesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatingRequest
        )
    }

    private fun setupRecurringWork() {

        // UNMETERED network means that the OS reports that the user wont be charged for the network request.
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .apply {
                // Marshmallow +
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()


        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

}

