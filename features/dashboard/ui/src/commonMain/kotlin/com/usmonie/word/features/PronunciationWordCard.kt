package com.usmonie.word.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.BaseCard
import com.usmonie.word.features.ui.WordLargeResizableTitle
import com.usmonie.word.features.ui.WordMediumResizableTitle
import wtf.word.core.domain.tools.fastForEachIndexed

@Composable
fun DetailsWordCardLarge(
    onAudioPlayClicked: (String) -> Unit,
    onLearnClicked: (WordUi) -> Unit,
    onBookmarkedClicked: (WordUi) -> Unit,
    word: WordUi,
    bookmarked: Boolean,
    modifier: Modifier = Modifier
) {
    BaseCard(
        {},
        elevation = 4.dp,
        modifier = modifier
    ) {
        Spacer(Modifier.height(32.dp))
        WordLargeResizableTitle(word.word, Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        Spacer(Modifier.height(4.dp))
        if (!word.etymologyText.isNullOrBlank()) {
            EtymologyTitle()
            Spacer(Modifier.height(4.dp))
            Text(
                word.etymologyText,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(16.dp))
        }

        Pronunciations(word, onAudioPlayClicked)
        Spacer(Modifier.height(16.dp))
        WordCardButtons(
            { onLearnClicked(word) },
            { onBookmarkedClicked(word) },
            bookmarked,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
    }
}


@Composable
fun DetailsWordCardMedium(
    onAudioPlayClicked: (String) -> Unit,
    onLearnClicked: (WordUi) -> Unit,
    onBookmarkedClicked: (WordUi) -> Unit,
    bookmarked: Boolean,
    word: WordUi,
    modifier: Modifier = Modifier
) {
    var expanded by remember(word) { mutableStateOf(false) }
    val maxLines by remember(expanded) { mutableStateOf(if (expanded) Int.MAX_VALUE else 3) }
    BaseCard(
        {},
        elevation = 4.dp,
        modifier = modifier,
        enabled = false
    ) {
        Spacer(Modifier.height(20.dp))
        WordMediumResizableTitle(word.word, Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        Spacer(Modifier.height(16.dp))

        WordCardButtons(
            { onLearnClicked(word) },
            { onBookmarkedClicked(word) },
            bookmarked,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        if (word.etymologyText != null) {
            Column(Modifier.fillMaxWidth().clickable { expanded = !expanded }) {
                EtymologyTitle()
                Spacer(Modifier.height(4.dp))
                Text(
                    word.etymologyText,
                    maxLines = maxLines,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(16.dp))
            }

        }

        Pronunciations(word, onAudioPlayClicked)
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun Pronunciations(
    word: WordUi,
    onAudioPlayClicked: (String) -> Unit
) {
    val sounds by remember(word) {
        derivedStateOf {
            word.sounds
                .groupBy { sound -> sound.tags.joinToString { tag -> tag.replaceFirstChar { it.uppercaseChar() } } }
                .mapValues {
                    it.value.asSequence()
                        .map { pronunciation -> (pronunciation.ipa ?: pronunciation.enpr) }
                        .filterNotNull()
                        .filter { pronunciation -> pronunciation.isNotBlank() }
                        .joinToString { pronunciation -> pronunciation }
                }
                .toList()
                .filter { it.second.isNotBlank() }
        }
    }
    if (word.sounds.isNotEmpty()) {
        PronunciationTitle()
        Spacer(Modifier.height(4.dp))
        sounds.fastForEachIndexed { i, sound ->
            PronunciationItem(
                { },
                sound.first,
                sound.second,
                null
            )

            if (i in 0 until word.sounds.size - 1) {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
