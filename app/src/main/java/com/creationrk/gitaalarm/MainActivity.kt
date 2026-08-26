package com.creationrk.gitaalarm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.creationrk.gitaalarm.content.ProgressRepository
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import java.util.Locale

private val Sand = Color(0xFFF8F4EC)
private val Ink = Color(0xFF29251F)
private val Muted = Color(0xFF81786C)
private val Saffron = Color(0xFFB86B32)
private val Card = Color(0xFFF1EBE0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 7)
        }
        setContent { GitaAlarmApp(this) }
    }
}

@Composable
private fun GitaAlarmApp(activity: Activity) {
    val scheduler = remember { AlarmScheduler(activity) }
    var verse by remember { mutableStateOf(ProgressRepository(activity).current()) }
    var enabled by remember { mutableStateOf(scheduler.isEnabled) }
    var hour by remember { mutableIntStateOf(scheduler.hour) }
    var minute by remember { mutableIntStateOf(scheduler.minute) }
    var permissionMessage by remember { mutableStateOf<String?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> if (event == Lifecycle.Event.ON_RESUME) verse = ProgressRepository(activity).current() }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    MaterialTheme { Surface(Modifier.fillMaxSize(), color = Sand) {
        Column(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 20.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("namaste", color = Saffron, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(10.dp)); Text("Begin your day.", color = Ink, fontSize = 34.sp, fontWeight = FontWeight.Light)
                Spacer(Modifier.height(34.dp)); Text("TODAY'S SHLOKA", color = Muted, fontSize = 12.sp, letterSpacing = 1.8.sp)
                Spacer(Modifier.height(16.dp)); Text("॥ ${verse.chapter}.${verse.verse} ॥", color = Saffron, fontSize = 17.sp)
                Spacer(Modifier.height(18.dp)); Text(verse.sanskrit, color = Ink, fontSize = 25.sp, lineHeight = 39.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(18.dp)); Text(verse.meaning, color = Muted, fontSize = 15.sp, lineHeight = 23.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(28.dp)); Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp)).background(Card).padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text("Bhagavad Gita", color = Ink, fontSize = 16.sp, fontWeight = FontWeight.SemiBold); Text("Chapter ${verse.chapter} · Verse ${verse.verse} · ${verse.id}", color = Muted, fontSize = 13.sp) }
                    Text("Offline", color = Saffron, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) { Text("Morning alarm", color = Muted, fontSize = 13.sp); Text(String.format(Locale.getDefault(), "%02d:%02d", hour, minute), color = Ink, fontSize = 42.sp, fontWeight = FontWeight.Light) }
                    Switch(checked = enabled, onCheckedChange = { turnOn ->
                        if (!turnOn) { scheduler.cancel(); enabled = false }
                        else if (scheduler.scheduleDaily(hour, minute)) enabled = true
                        else { permissionMessage = "Allow Alarms & reminders to set your morning alarm."; activity.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)) }
                    })
                }
                Spacer(Modifier.height(12.dp)); Button(onClick = {
                    if (scheduler.scheduleTestAlarm()) enabled = true else { permissionMessage = "Allow Alarms & reminders, then tap again."; activity.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)) }
                }, Modifier.fillMaxWidth().height(54.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = Ink)) { Text("Test alarm in 5 minutes", color = Sand, fontSize = 15.sp) }
                permissionMessage?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Saffron, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
            }
        }
    } }
}
