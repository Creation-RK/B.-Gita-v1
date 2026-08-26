package com.creationrk.gitaalarm

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creationrk.gitaalarm.content.ProgressRepository
import com.creationrk.gitaalarm.content.Shloka

class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowWhenLocked(true); setTurnScreenOn(true)
        val verse = ProgressRepository(this).current()
        setContent { MorningExperience(verse, ::snooze, ::complete) }
    }
    private fun snooze() { AlarmAudioPlayer.stop(); AlarmScheduler(this).scheduleSnooze(); finishAlarm() }
    private fun complete() { AlarmAudioPlayer.stop(); ProgressRepository(this).completeCurrentAlarm(); AlarmScheduler(this).scheduleNextDailyAlarm(); finishAlarm() }
    private fun finishAlarm() { getSystemService(NotificationManager::class.java).cancel(AlarmReceiver.NOTIFICATION_ID); finish() }
}

@Composable
private fun MorningExperience(verse: Shloka, onSnooze: () -> Unit, onComplete: () -> Unit) {
    val sand = Color(0xFFF8F4EC); val ink = Color(0xFF29251F); val muted = Color(0xFF81786C); val saffron = Color(0xFFB86B32)
    MaterialTheme { Surface(Modifier.fillMaxSize(), color = sand) {
        Column(Modifier.fillMaxSize().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("namaste", color = saffron, fontSize = 17.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(10.dp)); Text("Begin your day.", color = ink, fontSize = 34.sp, fontWeight = FontWeight.Light)
            Spacer(Modifier.height(42.dp)); Text("॥ ${verse.chapter}.${verse.verse} ॥", color = saffron, fontSize = 18.sp)
            Spacer(Modifier.height(20.dp)); Text(verse.sanskrit, color = ink, fontSize = 25.sp, lineHeight = 39.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(22.dp)); Text("Bhagavad Gita · Chapter ${verse.chapter} · Verse ${verse.verse}", color = muted, fontSize = 14.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(10.dp)); Text("Recitation playing", color = muted, fontSize = 13.sp)
            Spacer(Modifier.height(44.dp)); Button(onClick = onComplete, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = ink)) { Text("I'm awake", color = sand) }
            Spacer(Modifier.height(12.dp)); Button(onClick = onSnooze, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = sand, contentColor = saffron)) { Text("Snooze for 10 minutes") }
        }
    } }
}
