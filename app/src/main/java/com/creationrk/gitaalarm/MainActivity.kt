package com.creationrk.gitaalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val Sand = Color(0xFFF8F4EC)
private val Ink = Color(0xFF29251F)
private val Muted = Color(0xFF81786C)
private val Saffron = Color(0xFFB86B32)
private val Card = Color(0xFFF1EBE0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GitaAlarmApp(this) }
    }
}

@Composable
private fun GitaAlarmApp(context: Context) {
    var enabled by remember { mutableStateOf(true) }
    var alarmHour by remember { mutableStateOf(6) }
    var alarmMinute by remember { mutableStateOf(0) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Sand) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("namaste", color = Saffron, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(10.dp))
                    Text("Begin your day.", color = Ink, fontSize = 34.sp, fontWeight = FontWeight.Light)
                    Spacer(Modifier.height(34.dp))

                    Text("TODAY'S SHLOKA", color = Muted, fontSize = 12.sp, letterSpacing = 1.8.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("॥ २.४७ ॥", color = Saffron, fontSize = 17.sp)
                    Spacer(Modifier.height(18.dp))
                    Text(
                        "कर्मण्येवाधिकारस्ते\nमा फलेषु कदाचन ।",
                        color = Ink,
                        fontSize = 28.sp,
                        lineHeight = 42.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(18.dp))
                    Text(
                        "You have a right to your actions,\nnot to the fruits of your actions.",
                        color = Muted,
                        fontSize = 15.sp,
                        lineHeight = 23.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Card).padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Bhagavad Gita", color = Ink, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("Chapter 2 · Verse 47", color = Muted, fontSize = 13.sp)
                        }
                        Text("Day 1", color = Saffron, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Morning alarm", color = Muted, fontSize = 13.sp)
                            Text(String.format(Locale.getDefault(), "%02d:%02d", alarmHour, alarmMinute), color = Ink, fontSize = 42.sp, fontWeight = FontWeight.Light)
                        }
                        Switch(checked = enabled, onCheckedChange = {
                            enabled = it
                            if (it) scheduleAlarm(context, alarmHour, alarmMinute) else cancelAlarm(context)
                        })
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            alarmMinute += 5
                            if (alarmMinute >= 60) { alarmMinute = 0; alarmHour = (alarmHour + 1) % 24 }
                            if (enabled) scheduleAlarm(context, alarmHour, alarmMinute)
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Ink)
                    ) { Text("Set next alarm", color = Sand, fontSize = 15.sp) }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Tomorrow · ${SimpleDateFormat("EEE, d MMM", Locale.getDefault()).format(Date(System.currentTimeMillis() + 86_400_000L))}",
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Muted, fontSize = 12.sp
                    )
                }
            }
        }
    }
}

private fun scheduleAlarm(context: Context, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(AlarmManager::class.java)
    val intent = Intent(context, MainActivity::class.java)
    val pending = PendingIntent.getActivity(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
    }
    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
}

private fun cancelAlarm(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    val pending = PendingIntent.getActivity(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    context.getSystemService(AlarmManager::class.java).cancel(pending)
}
