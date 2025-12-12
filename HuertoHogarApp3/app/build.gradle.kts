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

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-apache-2.0.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/LICENSE-notice.md"
        }
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

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel utilities for Compose (del profe)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

    // Activity Compose (del profe)
    implementation("androidx.activity:activity-compose:1.9.0")

    // 🔥 Necesario para cargar imágenes desde URL
    implementation("io.coil-kt:coil-compose:2.4.0")

    // ------------------------
    // PRUEBAS UNITARIAS (test)
    // ------------------------
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.8")

    // ------------------------
    // PRUEBAS INSTRUMENTADAS (androidTest)
    // ------------------------
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose UI Tests (si usas Compose)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Necesario para permitir acceso a actividades
    debugImplementation("androidx.compose.ui:ui-test-manifest")



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