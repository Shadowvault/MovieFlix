plugins {
    alias(libs.plugins.movieflix.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.paging.common)
    testImplementation(libs.junit)
}