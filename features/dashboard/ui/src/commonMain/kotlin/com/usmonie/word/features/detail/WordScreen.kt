package com.usmonie.word.features.detail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.GetSimilarWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.ui.BaseDashboardLazyColumn
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.TobBackButtonBar
import com.usmonie.word.features.ui.WordCard
import com.usmonie.word.features.ui.WordDetailsCard
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder

class WordScreen(private val wordViewModel: WordViewModel) : Screen(wordViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        val routeManager = LocalRouteManager.current
        val state by wordViewModel.state.collectAsState()
        val effect by wordViewModel.effect.collectAsState(null)

        WordEffect(effect)

        Scaffold(
            topBar = { TobBackButtonBar(routeManager::navigateBack, true) },
        ) {
            BaseDashboardLazyColumn(contentPadding = it) {
                item {
                    WordDetailsCard(
                        state.word,
                        wordViewModel::onUpdateFavouritePressed,
                        wordViewModel::onSharePressed,
                        wordViewModel::onFindSynonymPressed,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                if (!state.similarWords.item.isNullOrEmpty()) {
                    item {
                        Spacer(Modifier.height(12.dp))
                        MenuItemText("Similar words")
                    }

                    items(state.similarWords.item ?: listOf()) { word ->
                        WordCard(
                            word,
                            wordViewModel::onWordPressed,
                            wordViewModel::onUpdateFavouritePressed,
                            wordViewModel::onSharePressed,
                            wordViewModel::onFindSynonymPressed,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ID = "WordScreen"
        const val KEY = "WordExtra"

        data class WordExtra(val word: WordUi) : Extra {
            override val key: String = KEY
        }
    }

    class Builder(private val wordRepository: WordRepository) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            val wordExtra = requireNotNull(extra) as WordExtra

            return WordScreen(
                WordViewModel(
                    wordExtra,
                    UpdateFavouriteUseCaseImpl(wordRepository),
                    GetSimilarWordsUseCaseImpl(wordRepository)
                )
            )
        }
    }
}

@Composable
private fun WordEffect(effect: WordEffect?) {
    val routeManager = LocalRouteManager.current

    LaunchedEffect(effect) {
        when (effect) {
            is WordEffect.OpenWord -> routeManager.navigateTo(
                WordScreen.ID,
                extras = WordScreen.Companion.WordExtra(effect.word)
            )

            null -> Unit
        }
    }
}