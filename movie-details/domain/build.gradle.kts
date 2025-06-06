plugins {
    alias(libs.plugins.movieflix.jvm.library)
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)

    implementation(project(":core:domain"))
}