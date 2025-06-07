plugins {
    alias(libs.plugins.movieflix.android.library)
    alias(libs.plugins.movieflix.android.room)
}

android {
    namespace = "com.shadowvault.core.database"
}

dependencies {

    implementation(libs.paging.runtime)
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(project(":core:domain"))
}