import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
        .forEach {
            it.binaries.framework {
                baseName = "lib"
                xcf.add(this)
                isStatic = true
            }
        }

    dependencies {
        configurations
            .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
            .forEach {
                add(it.name, "io.mockative:mockative-processor:2.0.1")
            }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.multiplatform.compose.runtime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockative)
        }
    }
}

android {
    namespace = "io.tila"
    compileSdk = 34
    defaultConfig {
        minSdk = 29
    }
}
