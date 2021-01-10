package rhett.pezzuti.dailydose.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.utils.sendNotification


/** Use case is for scheduling something in the future. Not really necessary for what we're doing here. */
class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        
    }


}