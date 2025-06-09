plugins {
    alias(libs.plugins.movieflix.android.feature.ui)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.shadowvault.movie_details.presentation"
}

dependencies {
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)

    implementation(project(":core:domain"))
    implementation(project(":movie-details:domain"))

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.libraries)
    detektPlugins(libs.detekt.ruleAuthors)
}