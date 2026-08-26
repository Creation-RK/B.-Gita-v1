package com.creationrk.gitaalarm

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes

object AlarmAudioPlayer {
    private var player: MediaPlayer? = null

    fun play(context: Context, @RawRes audioResId: Int) {
        stop()
        player = MediaPlayer().apply {
            setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build())
            setDataSource(context.applicationContext, Uri.parse("android.resource://${context.packageName}/$audioResId"))
            prepare()
            setOnCompletionListener { stop() }
            setOnErrorListener { _, _, _ -> stop(); true }
            start()
        }
    }

    fun stop() { player?.run { if (isPlaying) stop(); release() }; player = null }
}
