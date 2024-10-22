plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.dogactanriverdi.dtimusic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dogactanriverdi.dtimusic"
        minSdk = 21
        targetSdk = 34
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Material Icons Extended
    implementation(libs.compose.material.icons.extended)

    // Navigation
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Accompanist Permissions
    implementation(libs.accompanist.permissions)

    // ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

}