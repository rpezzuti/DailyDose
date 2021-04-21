package rhett.pezzuti.dailydose.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import rhett.pezzuti.dailydose.R
import rhett.pezzuti.dailydose.data.Track

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotificationWithIntent(track: Track, applicationContext: Context) {


    val contentIntent = Intent(Intent.ACTION_VIEW, Uri.parse(track.url))
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
        .setContentTitle(track.title)
        .setContentText(track.artist + " • " + track.genre.toDisplayGenre())
        .setContentIntent(contentPendingIntent)

        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

}