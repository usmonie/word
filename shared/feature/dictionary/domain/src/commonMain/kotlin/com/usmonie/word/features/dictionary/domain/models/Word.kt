package com.usmonie.word.features.dictionary.domain.models

data class Word(
    val id: String,
    val word: String,
    val etymologyNumber: Int?,
    val etymologyText: String?,
    val lang: String,
    val langCode: String,
    val originalTitle: String?,
    val pos: String,
    val source: String?,
    val topics: List<String>,
    val translations: List<Translation>,
    val etymologyTemplates: List<EtymologyTemplate>,
    val headTemplates: List<HeadTemplate>,
    val inflectionTemplates: List<InflectionTemplate>,
    val forms: List<Form>,
    val meronyms: List<Related>,
    val synonyms: List<Related>,
    val formOf: List<Related>,
    val holonyms: List<Related>,
    val hypernyms: List<Related>,
    val hyphenation: List<String>,
    val hyponyms: List<Related>,
    val instances: List<Related>,
    val troponyms: List<Related>,
    val wikidata: List<String>,
    val wikipedia: List<String>,
    val abbreviations: List<Related>,
    val altOf: List<Related>,
    val antonyms: List<Related>,
    val categories: List<Category>,
    val coordinateTerms: List<Related>,
    val derived: List<Related>,
    val proverbs: List<Related>,
    val related: List<Related>,
    val senses: List<SenseCombined>,
    val sounds: List<Sound>,
    val descendants: List<Descendant>,
)