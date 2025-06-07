plugins {
    alias(libs.plugins.movieflix.android.application.compose)
    alias(libs.plugins.movieflix.jvm.ktor)
}

android {
    namespace = "com.shadowvault.movieflix"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.material.icons.extended)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Crypto
    implementation(libs.androidx.security.crypto.ktx)

    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Timber
    implementation(libs.timber)

    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation:ui"))
    implementation(project(":core:presentation:designsystem"))

    implementation(project(":auth:data"))
    implementation(project(":auth:domain"))
    implementation(project(":auth:presentation"))

    implementation(project(":home:data"))
    implementation(project(":home:domain"))
    implementation(project(":home:presentation"))

    implementation(project(":movie-details:data"))
    implementation(project(":movie-details:domain"))
    implementation(project(":movie-details:presentation"))
}