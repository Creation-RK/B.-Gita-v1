package com.creationrk.gitaalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/** Starts the active verse; it deliberately does not advance progress. */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val verse = com.creationrk.gitaalarm.content.ProgressRepository(context).current()
        AlarmAudioPlayer.play(context, verse.audioResId)
        createNotificationChannel(context)
        val alarmIntent = Intent(context, AlarmActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val openAlarm = PendingIntent.getActivity(context, 2001, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("namaste")
            .setContentText("Begin your day. ${verse.id} is ready.")
            .setStyle(NotificationCompat.BigTextStyle().bigText(verse.sanskrit))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setFullScreenIntent(openAlarm, true)
            .setContentIntent(openAlarm)
            .build()
        try { NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification) } catch (_: SecurityException) { }
        context.startActivity(alarmIntent)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "Morning shloka", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Daily Bhagavad Gita alarm"
            })
        }
    }

    companion object {
        const val CHANNEL_ID = "morning_shloka"
        const val NOTIFICATION_ID = 47
    }
}
