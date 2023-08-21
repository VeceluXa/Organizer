import com.danilovfa.organizer.Configuration
import com.danilovfa.organizer.Deps

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
    id("kotlin-parcelize")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(compose) {
                    implementation(runtime)
                    implementation(foundation)
                    implementation(material3)
                    implementation(material)
                    implementation(materialIconsExtended)
                    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                    implementation(components.resources)
                    implementation(uiTooling)
                    implementation(preview)
                }

                with (Deps.App.Cash.SQLDelight) {
                    implementation(runtime)
                    implementation(coroutinesExtensions)
                }

                with (Deps.Org.Jetbrains.KotlinX) {
                    implementation(serializationJson)
                    implementation(dateTime)
                }

                with (Deps.Io.InsertKoin) {
                    implementation(koinCore)
                    implementation(koinCompose)
                }

                with (Deps.Com.Arkivanov.MviKotlin) {
                    implementation(mvikotlin)
                    implementation(mvikotlinMain)
                    implementation(mvikotlinExtensionsCoroutines)
                }

                with (Deps.Com.Arkivanov.Decompose) {
                    implementation(decompose)
                    implementation(extensionsCompose)
                }

                implementation(Deps.Com.Arkivanov.Essenty.lifecycle)
                implementation(Deps.Co.Touchlab.kermit)

                with (Deps.Dev.IceRock.Moko.Resources) {
                    implementation(resources)
                    implementation(resourcesCompose)
                }
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.AndroidX.core)
                implementation(Deps.App.Cash.SQLDelight.androidDriver)
                implementation(Deps.Io.InsertKoin.koinCore)
                implementation(Deps.Io.InsertKoin.koinAndroid)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

sqldelight {
    databases {
        create("TasksDatabase") {
            packageName.set("com.danilovfa.organizer.tasksDatabase")
        }
    }
}

android {
    namespace = "com.danilovfa.organizer"
    compileSdk = Configuration.compileSdk
    defaultConfig {
        minSdk = Configuration.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

multiplatformResources {
    multiplatformResourcesPackage = "com.danilovfa.organizer.resources"
}