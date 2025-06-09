plugins {
    alias(libs.plugins.movieflix.android.feature.ui)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.shadowvault.home.presentation"
}

dependencies {
    implementation(libs.paging.compose)
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
    implementation(project(":home:domain"))

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.libraries)
    detektPlugins(libs.detekt.ruleAuthors)
}