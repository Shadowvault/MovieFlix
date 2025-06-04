plugins {
    alias(libs.plugins.movieflix.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.shadowvault.core.presentation.ui"
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    implementation(project(":core:domain"))
    implementation(project(":core:presentation:designsystem"))
}