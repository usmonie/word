package com.usmonie.word.features.dashboard.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.compass.viewmodel.ConnectionErrorState
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.compass.viewmodel.updateData
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.dashboard.ui.models.SubscriptionSaleStateUi
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

@Immutable
internal data class DashboardState(
    val subscriptionSaleState: SubscriptionSaleStateUi? = null,
    val showSubscriptionAd: Boolean = true,
    val subscriptionStatus: SubscriptionStatus = SubscriptionStatus.None,
    val searchFieldState: SearchFieldState = SearchFieldState(),
    val wordOfTheDay: ContentState<WordCombinedUi> = ContentState.Loading(),
    val randomWord: ContentState<WordCombinedUi> = ContentState.Loading(),
    val foundWords: ContentState<List<WordCombinedUi>> = ContentState.Success(listOf()),
    val recentSearch: List<WordCombinedUi> = listOf(),
) : ScreenState {

    fun DashboardEvent.Content.toState() = this@DashboardState.copy(
        subscriptionStatus = subscriptionStatus,
        searchFieldState = searchFieldState,
        wordOfTheDay = wordOfTheDay,
        randomWord = randomWord,
        foundWords = foundWords,
        recentSearch = recentSearch
    )
}

@Stable
internal data class SearchFieldState(
    var hasFocus: Boolean = false,
    var searchFieldValue: TextFieldValue = TextFieldValue()
)

internal sealed class DashboardAction : ScreenAction {
    data object OnBackToMain : DashboardAction()
    data object OnLoadData : DashboardAction()
    data object OnNextRandomWord : DashboardAction()

    data class OnOpenWord(val wordCombined: WordCombinedUi) : DashboardAction()
    data class OnOpenSearchWord(val wordCombined: WordCombinedUi) : DashboardAction()
    data class OnFavoriteWord(val wordCombined: WordCombinedUi) : DashboardAction()
    data class OnUpdateSubscriptionState(val collapsed: Boolean) : DashboardAction()
    data class OnInputQuery(val query: TextFieldValue) : DashboardAction()
    data class OnQueryFieldFocusChange(val focused: Boolean) : DashboardAction()

    data class OnMenuItemClicked(val menuItem: DashboardMenuItem) : DashboardAction()
}

internal sealed class DashboardEvent : ScreenEvent {
    data class Content(
        val recentSearch: List<WordCombinedUi>,
        val wordOfTheDay: ContentState<WordCombinedUi> = ContentState.Error(
            ConnectionErrorState(Exception("Error while loading"))
        ),
        val randomWord: ContentState<WordCombinedUi>,
        val foundWords: ContentState<List<WordCombinedUi>>,
        val subscriptionStatus: SubscriptionStatus
    ) : DashboardEvent()

    data class OnMenuItemClicked(val menuItem: DashboardMenuItem) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class QueryFocusChanged(val hasFocus: Boolean) : DashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : DashboardEvent()
    data class UpdateWord(val word: WordCombinedUi) : DashboardEvent()

    data object BackToMain : DashboardEvent()
    data object ContentLoading : DashboardEvent()
}

internal sealed class DashboardEffect : ScreenEffect {
    data class OnMenuItemClicked(val menuItem: DashboardMenuItem) : DashboardEffect()

    data class OpenWord(val word: WordCombinedUi) : DashboardEffect()
}

internal fun DashboardState.toContentEvent(): DashboardEvent.Content {
    return DashboardEvent.Content(
        recentSearch,
        wordOfTheDay,
        randomWord,
        foundWords,
        subscriptionStatus
    )
}

internal fun DashboardState.toContentEventWithUpdatedWord(word: WordCombinedUi): DashboardEvent.Content {
    return DashboardEvent.Content(
        recentSearch.fastUpdate(word),
        wordOfTheDay.fastUpdate(word),
        randomWord.fastUpdate(word),
        foundWords.fastUpdateList(word),
        subscriptionStatus
    )
}

internal fun ContentState<List<WordCombinedUi>>.fastUpdateList(word: WordCombinedUi) =
    updateData { it.fastUpdate(word) }

internal fun ContentState<WordCombinedUi>.fastUpdate(word: WordCombinedUi) =
    updateData { if (it.word == word.word) word else it }

internal fun List<WordCombinedUi>.fastUpdate(word: WordCombinedUi) =
    fastMap { if (it.word == word.word) word else it }