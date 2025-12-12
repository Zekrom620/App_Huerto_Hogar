plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Mantienes el plugin de Compose
    alias(libs.plugins.kotlin.compose)
    // Mantienes el plugin de KSP para Room
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "cl.huertohogar.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "cl.huertohogar.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Mantienes la compatibilidad con Java 11
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- DEPENDENCIAS ORIGINALES (ROOM, NAVIGATION) ---
    // Room
    val roomVersion = "2.8.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // ViewModel y LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // --- DEPENDENCIAS MAPA Y LOCALIZACIÓN AÑADIDAS ---

    // 🗺️ Mapbox dependencies (para mostrar el mapa)
    implementation("com.mapbox.maps:android-ndk27:11.16.2")
    implementation("com.mapbox.extension:maps-compose-ndk27:11.16.2")

    // 📍 Google Play Services Location (para obtener la ubicación del usuario)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    // Para usar la ubicación en coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0")

    // --- DEPENDENCIAS BASE DE COMPOSE ---

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended") // De tu código original
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}