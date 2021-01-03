package rhett.pezzuti.dailydose.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import rhett.pezzuti.dailydose.R

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.track_notification_channel)
    )
        .setSmallIcon(R.drawable.ic_eigth_note)
        .setContentTitle(applicationContext.getString(R.string.track_notification_channel_title))
        .setContentText(applicationContext.getString(R.string.track_notification_channel_text))

    notify(NOTIFICATION_ID, builder.build())

}