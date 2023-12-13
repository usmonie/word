package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.new.components.SearchWordCard
import com.usmonie.word.features.new.details.WordDetailsScreen
import com.usmonie.word.features.new.favorites.FavoritesScreen
import com.usmonie.word.features.new.games.hangman.HangmanGameScreen
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.ui.AdMob
import com.usmonie.word.features.ui.BaseDashboardLazyColumn
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.MenuItemText
import com.usmonie.word.features.ui.SearchBar
import com.usmonie.word.features.ui.TopBackButtonBar
import com.usmonie.word.features.ui.VerticalAnimatedVisibility
import com.usmonie.word.features.ui.WordRecentCard
import com.usmonie.word.features.ui.WordsLazyRow
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.ui.AdKeys
import wtf.speech.core.ui.ContentState
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.domain.Analytics

class DashboardScreen(
    private val changeTheme: (WordColors) -> Unit,
    private val changeFont: (WordTypography) -> Unit,
    private val dashboardViewModel: DashboardViewModel,
    private val adMob: AdMob
) : Screen(dashboardViewModel) {

    override val id: String = ID

    @Composable
    override fun Content() {
        val state by dashboardViewModel.state.collectAsState()
        val effect by dashboardViewModel.effect.collectAsState(null)
        val localFocusManager = LocalFocusManager.current
        val listState = rememberLazyListState()

        val onPointerInput: suspend PointerInputScope.() -> Unit = remember {
            { detectTapGestures(onTap = { localFocusManager.clearFocus() }) }
        }
        DashboardEffects(listState, localFocusManager, effect)

        Scaffold(
            topBar = {
                TopBackButtonBar(
                    dashboardViewModel::onBackClick,
                    state.query.text.isNotBlank()
                )
            },
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) { detectTapGestures(onTap = { localFocusManager.clearFocus() }) }
        ) { insets ->
            MainState(
                onPointerInput,
                listState,
                insets,
                state,
                adMob,
            )
        }
    }

    @Composable
    private fun DashboardEffects(
        listState: LazyListState,
        localFocusManager: FocusManager,
        effect: DashboardEffect?
    ) {
        val routeManager = LocalRouteManager.current
        LaunchedEffect(listState.firstVisibleItemScrollOffset) {
            if (listState.firstVisibleItemScrollOffset > 100) {
                localFocusManager.clearFocus()
            }
        }

        LaunchedEffect(effect) {
            when (effect) {
                is DashboardEffect.ChangeTheme -> {
                    changeFont(effect.wordTypography)
                    changeTheme(effect.wordColors)
                }

                is DashboardEffect.OpenFavourites -> routeManager.navigateTo(FavoritesScreen.ID)
                is DashboardEffect.OpenWord -> routeManager.navigateTo(
                    WordDetailsScreen.ID,
                    extras = WordDetailsScreen.Companion.WordExtra(effect.word)
                )

                is DashboardEffect.OpenHangman -> routeManager.navigateTo(
                    HangmanGameScreen.ID,
                    extras = HangmanGameScreen.Extras(effect.word)
                )

                null -> Unit
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MainState(
        onPointerInput: suspend PointerInputScope.() -> Unit,
        listState: LazyListState,
        insets: PaddingValues,
        state: DashboardState,
        adMob: AdMob,
    ) {
        val (hasFocus, onFocusChange) = remember { mutableStateOf(false) }
        Box {
            BaseDashboardLazyColumn(listState, insets) {
                item {
                    SearchBar(
                        dashboardViewModel::onQueryChanged,
                        placeholder = "[S]earch",
                        query = state.query,
                        modifier = Modifier.fillMaxWidth().testTag("DASHBOARD_SEARCH_BAR"),
                        hasFocus = hasFocus,
                        enabled = state.wordOfTheDay is ContentState.Success,
                        onFocusChange = onFocusChange,
                    )
                }

                item {
                    VerticalAnimatedVisibility(hasFocus && state.recentSearch.isNotEmpty()) {
                        RecentCards(
                            dashboardViewModel::onOpenWord,
                            state.recentSearch,
                        )
                    }
                }

                if (state.foundWords is ContentState.Loading && state.query.text.isNotBlank()) {
                    item {
                        LoadingProgress()
                    }
                }

                if (state.query.text.isNotBlank()) {
                    items(
                        state.foundWords.item ?: listOf(),
                        key = { word -> word.word }
                    ) { word ->
                        SearchWordCard(
                            dashboardViewModel::onOpenWord,
                            dashboardViewModel::onUpdateFavouritesPressed,
                            dashboardViewModel::onShareWord,
                            {},
//                            dashboardViewModel::onSynonymClicked,
                            word,
                            Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .animateItemPlacement()
                                .pointerInput(Unit, onPointerInput),
                        )
                    }
                }

                item {
                    VerticalAnimatedVisibility(state.query.text.isBlank()) {
                        WordOfTheDayMenuItem(
                            onWordClick = dashboardViewModel::onOpenWord,
                            onAddFavouritePressed = dashboardViewModel::onUpdateFavouritesPressed,
                            onSharePressed = dashboardViewModel::onShareWord,
                            onUpdatePressed = dashboardViewModel::onUpdateRandomCard,
                            showWordOfTheDay = state.showWordOfTheDay,
                            word = state.wordOfTheDay
                        )
                    }
                }

                item {
                    Games(
                        dashboardViewModel::onGamesClicked,
                        dashboardViewModel::onHangman,
                        onPointerInput,
                        state.query.text,
                        state.showGames,
                    )
                }

                item {
                    FavouritesMenuItem(
                        state.query.text.isBlank(),
                        dashboardViewModel::onFavouritesItemClicked,
                        Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
                    )
                }

                item {
                    Settings(
                        dashboardViewModel::onSettingsItemClicked,
                        dashboardViewModel::onChangeColors,
                        dashboardViewModel::onChangeFonts,
                        dashboardViewModel::onClearRecentHistory,
                        onPointerInput,
                        state.query.text,
                        state.showSettings,
                    )
                }

                item {
                    About(
                        dashboardViewModel::onSettingsItemClicked,
                        {},
                        {},
                        {},
                        onPointerInput,
                        state.query.text,
                        state.showAbout
                    )
                }

                item { Spacer(Modifier.height(80.dp)) }
            }

            if (!state.subscribed) {
                adMob.Banner(
                    AdKeys.BANNER_ID,
                    Modifier.fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(insets)
                )
            }
        }
    }

    @Composable
    private fun LoadingProgress() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
    }

    companion object {
        const val ID = "DASHBOARD_SCREEN"
    }

    class Builder(
        private val changeTheme: (WordColors) -> Unit,
        private val changeFont: (WordTypography) -> Unit,
        private val userRepository: UserRepository,
        private val wordRepository: WordRepository,
        private val adMob: AdMob,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?) = DashboardScreen(
            changeTheme,
            changeFont,
            DashboardViewModel(
                SearchWordsUseCaseImpl(wordRepository),
                GetSearchHistoryUseCaseImpl(wordRepository),
                GetWordOfTheDayUseCaseImpl(wordRepository),
                UpdateFavouriteUseCaseImpl(wordRepository),
                CurrentThemeUseCaseImpl(userRepository),
                RandomWordUseCaseImpl(wordRepository),
                ChangeThemeUseCaseImpl(userRepository),
                ClearRecentUseCaseImpl(wordRepository),
                analytics
            ),
            adMob
        )
    }
}

@Composable
private fun RecentCards(
    onWordClick: (WordCombinedUi) -> Unit,
    words: List<WordCombinedUi>
) {
    Column {
        MenuItemText("Recent")
        Spacer(Modifier.height(8.dp))
        WordsLazyRow(words) { wordUi ->
            WordRecentCard(
                wordUi,
                { word -> onWordClick(word) },
                modifier = Modifier.fillParentMaxWidth(0.75f)
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun FavouritesMenuItem(
    showItem: Boolean,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    VerticalAnimatedVisibility(showItem) {
        Column {
            MenuItem(onMenuClick, "Favorites", modifier.testTag("FAVOURITES_ITEM"))
        }
    }
}