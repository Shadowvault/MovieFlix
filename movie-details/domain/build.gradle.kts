plugins {
    alias(libs.plugins.movieflix.jvm.library)
    alias(libs.plugins.detekt)
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)

    implementation(project(":core:domain"))

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.libraries)
    detektPlugins(libs.detekt.ruleAuthors)
}