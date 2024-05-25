
import extensions.commonDependencies

plugins {
    alias(libs.plugins.jetbrainsCompose)
    id(libs.plugins.usmonie.multiplatform.ui.get().pluginId)
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
    alias(libs.plugins.compose.compiler)
}

android.namespace = "com.usmonie.word.features.favorites.ui"

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
}

commonDependencies {
    implementation(projects.compass.core)
    implementation(projects.compass.viewmodel)
    implementation(projects.shared.core.kit)
    implementation(projects.shared.core.tools)

    implementation(projects.shared.feature.dictionary.domain)
    implementation(projects.shared.feature.dictionary.ui)

    implementation(projects.shared.feature.subscriptions.domain)
    implementation(projects.shared.feature.subscriptions.ui)

    implementation(libs.compose.material3.jetbrains)
}

kotlin {
    applyDefaultHierarchyTemplate()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.usmonie.word.features.favorites.ui"

    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

task("testClasses")
