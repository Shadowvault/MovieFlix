plugins {
    alias(libs.plugins.movieflix.android.feature.ui)
}

android {
    namespace = "com.shadowvault.auth.presentation"
}

dependencies {
    implementation(libs.timber)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    implementation(project(":core:domain"))
    implementation(project(":auth:domain"))
}