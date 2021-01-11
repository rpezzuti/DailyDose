package rhett.pezzuti.dailydose.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import rhett.pezzuti.dailydose.MainActivity
import rhett.pezzuti.dailydose.R

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_eigth_note)
        .setContentTitle(applicationContext.getString(R.string.track_notification_channel_title))
        .setContentText(messageBody)

    notify(NOTIFICATION_ID, builder.build())

}

fun NotificationManager.sendNotificationWithIntent(trackTitle: String, trackArtist: String, trackUrl: String, applicationContext: Context) {


    val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trackUrl))
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        0,
        contentIntent,
        PendingIntent.FLAG_ONE_SHOT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_eigth_note)
        .setContentTitle(trackTitle)
        .setContentText(trackArtist)
        .setContentIntent(contentPendingIntent)

        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

}