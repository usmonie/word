package com.usmonie.word.features.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface PlatformAds {
	fun getBannerAd(modifier: Modifier): @Composable () -> Unit
	fun getRewardedLifeInterstitial(): @Composable (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit
	fun getRewardedHintInterstitial(): @Composable (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit
	fun getRewardedNewGameInterstitial(): @Composable (onAddDismissed: () -> Unit, onRewardGranted: (Int) -> Unit) -> Unit
	fun getInterstitial(): @Composable () -> Unit
	fun getAdsState(): AdsManagerState
}
