package com.usmonie.word.features.dictionary.ui.models

data class WordLearnUi(
    val wordCombined: WordCombinedUi,
    val lastRepetition: Long,
    val interval: Long,
    val easyFactor: Long
)