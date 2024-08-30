import UIKit
import SwiftUI
import ComposeApp
import GoogleMobileAds

class ComposeViewController: UIViewController {
	private var iosAds: IOSPlatformAds!

	override func viewDidLoad() {
		super.viewDidLoad()

		iosAds = IOSPlatformAds(rootViewController: self)

		let mainViewController = MainViewControllerKt.MainViewController(
			analytics: NativeAnalytics(),
			bannerAd: { SwiftView(content: Banner(bannerID: "ca-app-pub-2198867984469198/3121295852")) },
			showInterstitialAd: { self.iosAds.showInterstitial(completion: {}) },
			showRewardedLifeInterstitialAd: { [weak self] onDismissed, onReward in
				self?.iosAds.showRewardedAd(onReward: { amount in onReward(KotlinInt(value: amount)) }, onDismissed: { onDismissed() })
			},
			showRewardedHintInterstitialAd: { [weak self] onDismissed, onReward in
				self?.iosAds.showRewardedAd(onReward: { amount in onReward(KotlinInt(value: amount)) }, onDismissed: { onDismissed() })
			},
			showRewardedNewGameInterstitialAd: { [weak self] onDismissed, onReward in
				self?.iosAds.showRewardedAd(onReward: { amount in onReward(KotlinInt(value: amount)) }, onDismissed: { onDismissed() })
			},
			getAdsManagerState: { [weak self] in
				guard let state = self?.iosAds.getAdsState() else {
					return UiAdsManagerState(isInterstitialReady: false, isRewardLifeReady: false, isRewardHintReady: false, isRewardNewGameReady: false, isBannerReady: false)
				}
				return UiAdsManagerState(
					isInterstitialReady: state["isInterstitialReady"] ?? false,
					isRewardLifeReady: state["isRewardLifeReady"] ?? false,
					isRewardHintReady: state["isRewardHintReady"] ?? false,
					isRewardNewGameReady: state["isRewardNewGameReady"] ?? false,
					isBannerReady: state["isBannerReady"] ?? true
				)
			}
		)

		addChild(mainViewController)
		view.addSubview(mainViewController.view)
		mainViewController.view.frame = view.bounds
		mainViewController.didMove(toParent: self)
	}
}


struct ComposeView: UIViewControllerRepresentable {
	func makeUIViewController(context: Context) -> UIViewController {
		GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers =
			["4d46112c6bb0f50c7eea15364843c6d3"]

		return ComposeViewController()
	}

	func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
	}
}

struct ContentView: View {
	var body: some View {
		ComposeView()
			.ignoresSafeArea(.keyboard) // Compose has own keyboard handler
			.edgesIgnoringSafeArea(.top)
			.edgesIgnoringSafeArea(.bottom)
	}
}
