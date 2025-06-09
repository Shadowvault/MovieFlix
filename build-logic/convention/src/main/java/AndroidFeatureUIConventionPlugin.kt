import com.shadowvault.convention.addUILayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUIConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("movieflix.android.library.compose")
                apply("org.jetbrains.kotlin.plugin.parcelize")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                addUILayerDependencies(target)
                DependencyHandlerScope.apply {
                    "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                }
            }
        }
    }
}