@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.konan.target.CompilerOutputKind

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.androidx.baselineprofile)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            version = "1.0.0"

            dependencies {
                implementation(projects.core.design)
                implementation(projects.features.dashboard.domain)
                implementation(projects.features.dashboard.ui)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.features.dashboard.ui)
                implementation(projects.features.dashboard.domain)
                implementation(projects.features.dashboard.data)

                implementation(projects.compass.core)
                implementation(projects.core.design)
                implementation(projects.core.domain)
                implementation(projects.core.ui)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation(libs.sqldelight.runtime)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.activity)
            }
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
            linkerOpts.add("-lsqlite3")

//            export(projects.core.design)
            transitiveExport = true
        }
    }
}

android {
    namespace = "com.usmonie.word"
    compileSdk = 34 // config.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.usmonie.word"
        minSdk = 28 //config.versions.android.minSdk.get().toInt()
        targetSdk = 34

        versionCode = 2
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"// libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
copyNativeResources("commonMain")
dependencies {
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":baselineprofile"))
}

fun copyNativeResources(sourceSet: String) {
    if (sourceSet.isEmpty()) throw IllegalStateException("Valid sourceSet required")

    val prefix = "copy${sourceSet.capitalize()}Resources"

    tasks.withType<KotlinNativeLink> {
        val firstIndex = name.indexOfFirst { it.isUpperCase() }
        val taskName = "$prefix${name.substring(firstIndex)}"

        dependsOn(
            tasks.register<Copy>(taskName) {
                from("../core/design/src/$sourceSet/resources")
                when (outputKind) {
                    CompilerOutputKind.FRAMEWORK -> into(outputFile.get())
                    CompilerOutputKind.PROGRAM -> into(destinationDirectory.get())
                    else -> throw IllegalStateException("Unhandled binary outputKind: $outputKind")
                }
            }
        )
    }

    tasks.withType<FatFrameworkTask> {
        if (destinationDir.path.contains("Temp")) return@withType

        val firstIndex = name.indexOfFirst { it.isUpperCase() }
        val taskName = "$prefix${name.substring(firstIndex)}"

        dependsOn(
            tasks.register<Copy>(taskName) {
                from("../core/design/src/$sourceSet/resources")
                into(fatFramework)
            }
        )
    }
}
