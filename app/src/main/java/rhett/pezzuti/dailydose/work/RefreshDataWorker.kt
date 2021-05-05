package rhett.pezzuti.dailydose.work


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.net.HttpRetryException

class RefreshDataWorker (appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {

        // Need to get the context from the activity

        return try {
            // repository.refreshTracks()
            Result.retry()
        } catch (exception: HttpRetryException) {
            Result.retry()
        }
    }
}