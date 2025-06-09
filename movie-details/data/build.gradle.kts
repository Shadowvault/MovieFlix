plugins {
    alias(libs.plugins.movieflix.android.library)
    alias(libs.plugins.movieflix.jvm.ktor)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.shadowvault.movie_details.data"
}

dependencies {
    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":movie-details:domain"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.libraries)
    detektPlugins(libs.detekt.ruleAuthors)
}