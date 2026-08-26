package com.creationrk.gitaalarm.content

import android.content.Context

/** Small MVP persistence store. Progress changes only when an alarm is completed. */
class ProgressRepository(context: Context) {
    private val preferences = context.getSharedPreferences("gita_progress", Context.MODE_PRIVATE)

    fun current(): Shloka = GitaContentRepository.at(preferences.getInt(CURRENT_INDEX, 0))

    fun completeCurrentAlarm(): Shloka {
        val next = GitaContentRepository.nextIndex(preferences.getInt(CURRENT_INDEX, 0))
        preferences.edit().putInt(CURRENT_INDEX, next).apply()
        return GitaContentRepository.at(next)
    }

    companion object { private const val CURRENT_INDEX = "current_index" }
}
