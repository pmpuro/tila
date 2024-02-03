plugins {
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.binary.compatibility.validator).apply(false)
}
