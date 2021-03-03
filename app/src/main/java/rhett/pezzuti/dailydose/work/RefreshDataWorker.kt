package rhett.pezzuti.dailydose.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import rhett.pezzuti.dailydose.data.source.DefaultTrackRepository
import rhett.pezzuti.dailydose.data.source.local.getInstance
import java.net.HttpRetryException

class RefreshDataWorker (appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = DefaultTrackRepository(database)

        return try {
            // repository.refreshTracks()
            Result.retry()
        } catch (exception: HttpRetryException) {
            Result.retry()
        }
    }
}