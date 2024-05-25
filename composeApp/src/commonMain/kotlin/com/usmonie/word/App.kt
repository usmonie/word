package com.usmonie.word

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.ui.Root
import com.usmonie.core.kit.design.themes.WordTheme
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.ModernChic
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.settings.ui.models.UserSelectedTheme
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCase
import org.koin.compose.koinInject

@Composable
fun App(
    routeManager: RouteManager = koinInject(),
    userSelectedThemeUseCase: UserSelectedThemeUseCase = koinInject(),
) {
    val isSystemInDark = isSystemInDarkTheme()
    val currentTheme by userSelectedThemeUseCase(Unit).collectAsState(
        UserSelectedTheme(
            WordThemes.DEEP_INDIGO,
            ModernChic,
            DarkThemeMode.NEVER
        )
    )

    WordTheme(
        currentTheme.themes,
        currentTheme.typography,
        currentTheme.darkThemeMode == DarkThemeMode.ALWAYS ||
            (currentTheme.darkThemeMode == DarkThemeMode.AUTO && isSystemInDark)
    ) {
        Root(routeManager, isGestureNavigationEnabled = true)
    }
}
