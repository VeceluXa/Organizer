plugins {
    kotlin("jvm") version "1.8.20" apply false
    kotlin("multiplatform") version "1.8.20" apply false
    kotlin("android") version "1.8.20" apply false
    kotlin("plugin.serialization") version "1.8.20" apply false
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.compose") version "1.5.0-beta02" apply false
    id("app.cash.sqldelight") version "2.0.0" apply false
    id("dev.icerock.mobile.multiplatform-resources") version "0.23.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
