plugins {
    alias(libs.plugins.movieflix.android.library)
    alias(libs.plugins.movieflix.jvm.ktor)
}

android {
    namespace = "com.shadowvault.auth.data"
}

dependencies {
    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":auth:domain"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}