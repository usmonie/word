package com.usmonie.word.features.dictionary.domain.models

data class Descendant(
    val id: String,
    val depth: Int?,
    val tags: List<String>,
    val templates: List<Template>,
    val text: String?
)