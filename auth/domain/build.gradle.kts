plugins {
    alias(libs.plugins.movieflix.jvm.library)
}

dependencies {
    testImplementation(libs.junit)

    implementation(project(":core:domain"))
}