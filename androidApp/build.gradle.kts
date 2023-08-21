import com.danilovfa.organizer.Configuration
import com.danilovfa.organizer.Deps

plugins {
    kotlin("android")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.danilovfa.organizer.android"
    compileSdk = Configuration.compileSdk
    defaultConfig {
        applicationId = "com.danilovfa.organizer.android"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))

    with(compose) {
        implementation(foundation)
        implementation(material3)
        implementation(ui)
        implementation(preview)
        debugImplementation(uiTooling)
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

    with (Deps.Io.InsertKoin) {
        implementation(koinCore)
        implementation(koinAndroid)
    }

    with (Deps.AndroidX) {
        implementation(workRuntimeKtx)
        implementation(activityCompose)
        implementation(navigationCompose)
    }
}