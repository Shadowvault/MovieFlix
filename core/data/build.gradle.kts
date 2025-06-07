plugins {
    alias(libs.plugins.movieflix.android.library)
    alias(libs.plugins.movieflix.jvm.ktor)
}

android {
    namespace = "com.shadowvault.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    implementation(libs.jwtdecode)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core:domain"))
    implementation(project(":core:database"))
}