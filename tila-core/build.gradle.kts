import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

val libraryVersion = project.properties.getOrDefault("versionName", "0.0.1") as String
group = "dev.pmpuro.tila"
version = libraryVersion


kotlin {
    explicitApi()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("release", "debug")
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
        .forEach {
            it.binaries.framework {
                baseName = "tila"
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
    namespace = "dev.pmpuro.tila"
    compileSdk = 34
    defaultConfig {
        minSdk = 29
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "dev.pmpuro.tila"
            artifactId = "tila"
            version = libraryVersion

            pom {
                name = project.name
                description = "A concise description of my library"
                url = "https://github.com/pmpuro/tila"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
            }
        }
    }
}
