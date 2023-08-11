package com.danilovfa.organizer

object Deps {
    object App {
        object Cash {
            object SQLDelight {
                private const val nameSpace = "app.cash.sqldelight"
                const val gradlePlugin = "$nameSpace:gradle-plugin:${Versions.sqlDelight}"
                const val runtime = "$nameSpace:runtime:${Versions.sqlDelight}"
                const val coroutinesExtensions = "$nameSpace:coroutines-extensions:${Versions.sqlDelight}"
                const val androidDriver = "$nameSpace:android-driver:${Versions.sqlDelight}"
            }
        }
    }

    object Com {
        object Arkivanov {
            object Decompose {
                const val decompose = "com.arkivanov.decompose:decompose:${Versions.decompose}"
                const val extensionsCompose = "com.arkivanov.decompose:extensions-compose-jetbrains:${Versions.decompose}"
            }
            object MviKotlin {
                const val mvikotlin = "com.arkivanov.mvikotlin:mvikotlin:${Versions.mviKotlin}"
                const val mvikotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:${Versions.mviKotlin}"
                const val mvikotlinExtensionsCoroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${Versions.mviKotlin}"
            }
            object Essenty {
                const val lifecycle = "com.arkivanov.essenty:lifecycle:${Versions.essenty}"
            }
        }
    }

    object Org {
        object Jetbrains {
            object KotlinX {
                private const val nameSpace = "org.jetbrains.kotlinx"
                const val dateTime = "$nameSpace:kotlinx-datetime:${Versions.KotlinX.dateTime}"
                const val serializationJson = "$nameSpace:kotlinx-serialization-json:${Versions.KotlinX.serializationJson}"
            }
        }
    }

    object Io {
        object InsertKoin {
            private const val nameSpace = "io.insert-koin"
            const val koinCore = "$nameSpace:koin-core:${Versions.Koin.koin}"
            const val koinAndroid = "$nameSpace:koin-android:${Versions.Koin.koin}"
            const val koinCompose = "$nameSpace:koin-compose:${Versions.Koin.compose}"
        }
    }

    object Dev {
        object IceRock {
            object Moko {
                internal const val nameSpace = "dev.icerock.moko"
                object Resources {
                    const val gradlePlugin = "$nameSpace:resources-generator:${Versions.mokoResources}"
                    const val resources = "$nameSpace:resources:${Versions.mokoResources}"
                    const val resourcesCompose = "$nameSpace:resources-compose:${Versions.mokoResources}"
                }
            }
        }
    }

    object Co {
        object Touchlab {
            const val kermit = "co.touchlab:kermit:${Versions.kermit}"
        }
    }

    object AndroidX {
        const val core = "androidx.core:core:${Versions.AndroidX.core}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.AndroidX.activityCompose}"
        const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.AndroidX.workRuntimeKtx}"
        const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.AndroidX.navigationCompose}"
    }
}