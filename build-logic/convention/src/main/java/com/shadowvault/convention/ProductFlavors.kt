package com.shadowvault.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureProductFlavors(extensionType: ExtensionType) {
    when (extensionType) {
        ExtensionType.APPLICATION -> {
            extensions.configure<ApplicationExtension> {
                flavorDimensions += "env"
                productFlavors {
                    create("production") {
                        dimension = "env"
                        buildConfigField("String", "ENV_NAME", "\"Production\"")
                        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
                    }
                }
            }
        }

        ExtensionType.LIBRARY -> {
            extensions.configure<LibraryExtension> {
                flavorDimensions += "env"
                productFlavors {
                    create("production") {
                        dimension = "env"
                        buildConfigField("String", "ENV_NAME", "\"Production\"")
                        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
                    }
                }
            }
        }
    }
}


