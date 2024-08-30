import Foundation
import UIKit
import GoogleMobileAds

class IOSPlatformAds: NSObject {
	private var bannerView: GADBannerView?
	private var interstitial: GADInterstitialAd?
	private var rewardedAd: GADRewardedAd?

	private var isInterstitialReady = false
	private var isRewardedAdReady = false
	private var isBannerReady = false

	private var interstitialCompletion: (() -> Void)?
	private weak var rootViewController: UIViewController?

	init(rootViewController: UIViewController) {
		self.rootViewController = rootViewController
		super.init()
		setupAds()
	}

	private func setupAds() {
		// Setup banner
		bannerView = GADBannerView(adSize: GADAdSizeBanner)
		bannerView?.adUnitID = "ca-app-pub-2198867984469198/3121295852"
		bannerView?.rootViewController = UIApplication.shared.windows.first?.rootViewController
		bannerView?.load(GADRequest())
		bannerView?.delegate = self

		// Setup interstitial
		loadInterstitial()

		// Setup rewarded ad
		loadRewardedAd()
	}

	private func loadInterstitial() {
		let request = GADRequest()
		GADInterstitialAd.load(withAdUnitID: "ca-app-pub-2198867984469198/9599337622", request: request) { [weak self] ad, error in
			if let error = error {
				print("Failed to load interstitial ad with error: \(error.localizedDescription)")
				self?.isInterstitialReady = false
				return
			}
			self?.interstitial = ad
			self?.isInterstitialReady = true
		}
	}

	private func loadRewardedAd() {
		let request = GADRequest()
		GADRewardedAd.load(withAdUnitID: "ca-app-pub-2198867984469198/2195226998", request: request) { [weak self] ad, error in
			if let error = error {
				print("Failed to load rewarded ad with error: \(error.localizedDescription)")
				self?.isRewardedAdReady = false
				return
			}
			self?.rewardedAd = ad
			self?.isRewardedAdReady = true
		}
	}

	func showBanner(in view: UIView) {
		guard let bannerView = bannerView else {
			return
		}
		view.addSubview(bannerView)
		bannerView.translatesAutoresizingMaskIntoConstraints = false
		NSLayoutConstraint.activate([
										bannerView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
										bannerView.centerXAnchor.constraint(equalTo: view.centerXAnchor)
									])
	}

	func showInterstitial(completion: @escaping () -> Void) {
		guard let interstitial = interstitial else {
			completion()
			return
		}
		self.interstitialCompletion = completion

		interstitial.present(fromRootViewController: rootViewController)

		isInterstitialReady = false
		loadInterstitial()
	}

	func showRewardedAd(onReward: @escaping (Int32) -> Void, onDismissed: @escaping () -> Void) {
		guard let rewardedAd = rewardedAd else {
			onDismissed()
			return
		}
		rewardedAd.present(fromRootViewController: rootViewController) { [weak self] in
			let reward = rewardedAd.adReward
			onReward(Int32(reward.amount))
			self?.isRewardedAdReady = false
			self?.loadRewardedAd()
		}
		rewardedAd.fullScreenContentDelegate = self
	}

	func getAdsState() -> [String: Bool] {
		return [
			"isInterstitialReady": isInterstitialReady,
			"isRewardLifeReady": isRewardedAdReady,
			"isRewardHintReady": isRewardedAdReady,
			"isRewardNewGameReady": isRewardedAdReady,
			"isBannerReady": isBannerReady
		]
	}
}

extension IOSPlatformAds: GADBannerViewDelegate {
	func bannerViewDidReceiveAd(_ bannerView: GADBannerView) {
		print("Banner loaded successfully")
		isBannerReady = true
	}

	func bannerView(_ bannerView: GADBannerView, didFailToReceiveAdWithError error: Error) {
		print("Banner failed to load with error: \(error.localizedDescription)")
		isBannerReady = false
	}
}

extension IOSPlatformAds: GADFullScreenContentDelegate {
	func adDidDismissFullScreenContent(_ ad: GADFullScreenPresentingAd) {
		if ad is GADRewardedAd {
			loadRewardedAd()
		} else if ad is GADInterstitialAd {
			loadInterstitial()
			interstitialCompletion?()
			interstitialCompletion = nil
		}
	}

	func ad(_ ad: GADFullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
		print("Ad failed to present full screen content with error: \(error.localizedDescription)")
		if ad is GADInterstitialAd {
			interstitialCompletion?()
			interstitialCompletion = nil
		}
	}
}