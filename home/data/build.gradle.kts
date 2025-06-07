plugins {
    alias(libs.plugins.movieflix.android.library)
    alias(libs.plugins.movieflix.jvm.ktor)
}

android {
    namespace = "com.shadowvault.home.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.paging.runtime)
    implementation(libs.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":home:domain"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
}