import extensions.commonDependencies

plugins {
	id(libs.plugins.usmonie.multiplatform.domain.get().pluginId)
	kotlin("plugin.serialization") version "2.0.0"
}

android.namespace = "com.usmonie.word.features.games.domain"

kotlin {
	applyDefaultHierarchyTemplate()

	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	)

	commonDependencies {
		api(projects.shared.core.tools)
		api(projects.shared.core.domain)

		api("androidx.collection:collection:1.4.0")
		implementation(projects.shared.feature.quotes.domain)
	}
}

task("testClasses")
