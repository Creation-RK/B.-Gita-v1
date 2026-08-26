package com.creationrk.gitaalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmScheduler(private val context: Context) {
    private val preferences = context.getSharedPreferences("alarm_settings", Context.MODE_PRIVATE)
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    val isEnabled: Boolean get() = preferences.getBoolean(ENABLED, false)
    val hour: Int get() = preferences.getInt(HOUR, 6)
    val minute: Int get() = preferences.getInt(MINUTE, 0)
    val canScheduleExact: Boolean get() = alarmManager.canScheduleExactAlarms()

    fun scheduleDaily(hour: Int = this.hour, minute: Int = this.minute): Boolean {
        if (!canScheduleExact) return false
        preferences.edit().putBoolean(ENABLED, true).putInt(HOUR, hour).putInt(MINUTE, minute).apply()
        scheduleAt(nextOccurrence(hour, minute))
        return true
    }

    fun scheduleSnooze(minutes: Int = 10) {
        scheduleAt(System.currentTimeMillis() + minutes * 60_000L)
    }

    fun scheduleTestAlarm(minutes: Int = 5): Boolean {
        if (!canScheduleExact) return false
        preferences.edit().putBoolean(ENABLED, true).apply()
        scheduleAt(System.currentTimeMillis() + minutes * 60_000L)
        return true
    }

    fun scheduleNextDailyAlarm() {
        if (isEnabled && canScheduleExact) scheduleAt(nextOccurrence(hour, minute))
    }

    fun cancel() {
        preferences.edit().putBoolean(ENABLED, false).apply()
        alarmManager.cancel(pendingIntent())
    }

    private fun scheduleAt(time: Long) = alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent())

    private fun nextOccurrence(hour: Int, minute: Int): Long = Calendar.getInstance().run {
        set(Calendar.HOUR_OF_DAY, hour); set(Calendar.MINUTE, minute); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
        timeInMillis
    }

    private fun pendingIntent(): PendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    companion object { private const val ENABLED = "enabled"; private const val HOUR = "hour"; private const val MINUTE = "minute"; private const val REQUEST_CODE = 1001 }
}
