package com.usmonie.word.features.games.ui.models

import androidx.compose.ui.util.fastJoinToString
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.games.domain.models.Category
import com.usmonie.word.features.games.domain.models.Descendant
import com.usmonie.word.features.games.domain.models.EtymologyTemplate
import com.usmonie.word.features.games.domain.models.Example
import com.usmonie.word.features.games.domain.models.Form
import com.usmonie.word.features.games.domain.models.HeadTemplate
import com.usmonie.word.features.games.domain.models.InflectionTemplate
import com.usmonie.word.features.games.domain.models.Instance
import com.usmonie.word.features.games.domain.models.Related
import com.usmonie.word.features.games.domain.models.Sense
import com.usmonie.word.features.games.domain.models.SenseCombined
import com.usmonie.word.features.games.domain.models.Sound
import com.usmonie.word.features.games.domain.models.Template
import com.usmonie.word.features.games.domain.models.Translation
import com.usmonie.word.features.games.domain.models.Word
import com.usmonie.word.features.games.domain.models.WordCombined
import com.usmonie.word.features.games.domain.models.WordEtymology

fun Word.toUi() = WordUi(
    id,
    word = word,
    langCode = langCode,
    lang = lang,
    originalTitle = originalTitle,
    pos = pos,
    source = source,
    etymologyNumber = etymologyNumber,
    etymologyText = etymologyText,
    abbreviations = abbreviations.toUi(),
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.fastMap { it.toUi() },
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    descendants = descendants.fastMap { it.toUi() },
    etymologyTemplates = etymologyTemplates.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    forms = forms.fastMap { it.toUi() },
    headTemplates = headTemplates.fastMap { it.toUi() },
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyphenation = hyphenation,
    hyponyms = hyponyms.toUi(),
    inflectionTemplates = inflectionTemplates.fastMap { it.toUi() },
    instances = instances.toUi(),
    meronyms = meronyms.toUi(),
    proverbs = proverbs.toUi(),
    related = related.toUi(),
    senses = senses.fastMap { it.toUi() },
    sounds = sounds.fastMap { it.toUi() }.filterNot { it.transcription.isNullOrBlank() },
    synonyms = synonyms.toUi(),
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

fun List<Related>.toUi() = fastMap { it.toUi() }
fun Category.toUi() = CategoryUi(kind, langcode, name, orig, parents, source)

fun Descendant.toUi() =
    DescendantUi(depth, tags, templates.fastMap { it.toUi() }, text)

fun Form.toUi() =
    FormUi(
        form,
        headNr,
        ipa,
        roman,
        source,
        ruby,
        tags,
        topics
    )

fun Related.toUi() = RelatedUi(
    alt = alt,
    english = english,
    qualifier = qualifier,
    roman = roman,
    ruby = ruby.fastMap { it },
    sense = sense,
    source = source,
    tags = tags,
    taxonomic = taxonomic,
    topics = topics,
    urls = urls,
    word = word,
    extra = extra
)

fun Translation.toUi() = TranslationUi(
    alt = alt,
    code = code,
    english = english,
    lang = lang,
    note = note,
    roman = roman,
    sense = sense,
    tags = tags,
    taxonomic = taxonomic,
    topics = topics,
    word = word,
)

fun Sense.toUi() = SenseUi(
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.toUi(),
    compoundOf = compoundOf.toUi(),
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    examples = examples.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    glosses = glosses,
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyponyms = hyponyms.toUi(),
    instances = instances.fastMap { it.toUi() },
    links = links,
    meronyms = meronyms.toUi(),
    rawGlosses = rawGlosses,
    related = related.toUi(),
    synonyms = synonyms.toUi(),
    tags = tags,
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

fun SenseCombined.toUi(): SenseCombinedUi = SenseCombinedUi(
    gloss = gloss,
    children = children.fastMap { it.toUi() },
    qualifier = qualifier,
    taxonomic = taxonomic,
    headNr = headNr,
    altOf = altOf.toUi(),
    antonyms = antonyms.toUi(),
    categories = categories.toUi(),
    compoundOf = compoundOf.toUi(),
    coordinateTerms = coordinateTerms.toUi(),
    derived = derived.toUi(),
    examples = examples.fastMap { it.toUi() },
    formOf = formOf.toUi(),
    holonyms = holonyms.toUi(),
    hypernyms = hypernyms.toUi(),
    hyponyms = hyponyms.toUi(),
    instances = instances.fastMap { it.toUi() },
    links = links,
    meronyms = meronyms.toUi(),
    rawGlosses = rawGlosses,
    related = related.toUi(),
    synonyms = synonyms.toUi(),
    tags = tags,
    topics = topics,
    translations = translations.fastMap { it.toUi() },
    troponyms = troponyms.toUi(),
    wikidata = wikidata,
    wikipedia = wikipedia,
)

fun EtymologyTemplate.toUi() = EtymologyTemplateUi(
    expansion,
    name
)

fun Template.toUi() = TemplateUi(expansion, name)

fun HeadTemplate.toUi() = HeadTemplateUi(expansion, name)

fun InflectionTemplate.toUi() = InflectionTemplateUi(name)

fun Example.toUi() = ExampleUi(
    english = english,
    note = note,
    ref = ref,
    roman = roman,
    ruby = ruby,
    text = text,
    type = type,
)

fun Instance.toUi() = InstanceUi(sense, source, word, tags, topics)

fun Sound.toUi() = SoundUi(
    audio = audio,
    audioIpa = audio,
    transcription = ipa ?: enpr,
    form = form,
    homophone = homophone,
    mp3Url = mp3Url,
    note = note,
    oggUrl = oggUrl,
    other = other,
    rhymes = rhymes,
    tags = tags.fastJoinToString { tag ->
        tag.replaceFirstChar { c -> c.uppercaseChar() }
    },
    text = text,
    topics = topics,
    zhPron = zhPron,
)

fun WordCombined.toUi(): WordCombinedUi {
    return WordCombinedUi(word, wordEtymology.fastMap { it.toUi() }, isFavorite)
}

fun WordEtymology.toUi(): WordEtymologyUi {
    return WordEtymologyUi(
        etymologyText,
        etymologyNumber,
        sounds.fastMap { it.toUi() }.filterNot { it.transcription.isNullOrBlank() },
        words.fastMap { it.toUi() },
    )
}
