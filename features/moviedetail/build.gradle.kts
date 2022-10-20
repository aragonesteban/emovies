plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":features:shared"))

    implementation(AndroidX.lifecycle.runtimeKtx)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.fragment.ktx)
    implementation(AndroidX.Lifecycle.viewModelKtx)
    implementation(AndroidX.Lifecycle.runtimeKtx)
    implementation(Google.android.material)
    implementation(COIL)

    // Youtube
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")

    // Navigation Component
    implementation(AndroidX.navigation.fragmentKtx)

    // Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    // Tests
    testImplementation(Testing.junit4)
    testImplementation(Testing.Mockito.kotlin)
    testImplementation(Kotlin.test.testng)
    testImplementation(KotlinX.coroutines.test)
}