package com.creationrk.gitaalarm.content

import androidx.annotation.RawRes

data class Shloka(
    val id: String,
    val chapter: Int,
    val verse: Int,
    val sanskrit: String,
    val transliteration: String,
    val meaning: String,
    @RawRes val audioResId: Int
)
