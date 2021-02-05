package rhett.pezzuti.dailydose.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import rhett.pezzuti.dailydose.database.getInstance
import rhett.pezzuti.dailydose.repository.TrackRepository
import java.net.HttpRetryException

class RefreshDataWorker (appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = TrackRepository(database)

        return try {
            repository.refreshTracks()
            Result.retry()
        } catch (exception: HttpRetryException) {
            Result.retry()
        }
    }
}