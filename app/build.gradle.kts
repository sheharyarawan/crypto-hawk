plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin") version "2.8.3"
}

android {
    namespace = "com.example.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.demo"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)   // already points to appcompat
    implementation(libs.material)            // already points to material
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.airbnb.android:lottie:6.0.1")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Gson converter to parse JSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

// ViewModel (needed with LiveData in MVVM)a
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    // Room components
    implementation("androidx.room:room-runtime:2.6.1")

// For Kotlin support (coroutines, etc.)
    kapt("androidx.room:room-compiler:2.6.1")

// Optional: Room with coroutines support
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}