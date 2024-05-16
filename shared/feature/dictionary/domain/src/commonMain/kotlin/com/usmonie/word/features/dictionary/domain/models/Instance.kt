package com.usmonie.word.features.dictionary.domain.models


data class Instance(
    val id: String,
    val sense: String?,
    val source: String?,
    val tags: List<String>,
    val topics: List<String>,
    val word: String?
)
