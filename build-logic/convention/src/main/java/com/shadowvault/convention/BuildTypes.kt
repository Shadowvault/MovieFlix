package com.shadowvault.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(localProperties)
                        }
                        release {
                            configureReleaseBuildType(localProperties, commonExtension)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(localProperties)
                        }
                        release {
                            configureReleaseBuildType(localProperties, commonExtension)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(
    localProperties: Properties
) {

    buildConfigField("String", "OS", "\"Android\"")
    buildConfigField("String", "CLIENT_ID", "\"movieflix_android\"")
    buildConfigField("String", "FLAVOR", "\"Basic\"")
    buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")
}

private fun BuildType.configureReleaseBuildType(
    localProperties: Properties,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    buildConfigField("String", "OS", "\"Android\"")
    buildConfigField("String", "CLIENT_ID", "\"movieflix_android\"")
    buildConfigField("String", "FLAVOR", "\"Basic\"")
    buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")


    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}