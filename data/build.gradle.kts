plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.6.21"
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

}

dependencies {
    implementation(project(":domain"))

    implementation(KotlinX.coroutines.core)

    // Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    // Retrofit
    implementation(Square.retrofit2.retrofit)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)

    // Kotlin Serialization
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.json)

    // Room
    implementation(AndroidX.room.ktx)
    implementation(AndroidX.room.runtime)
    annotationProcessor(AndroidX.room.compiler)
    kapt(AndroidX.room.compiler)

    // Test
    testImplementation(Testing.junit4)
    testImplementation(Testing.MockK)
    testImplementation(Kotlin.test.testng)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.room.testing)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(Kotlin.test.testng)
    androidTestImplementation(AndroidX.test.espresso.core)
    androidTestImplementation(AndroidX.test.rules)
    androidTestImplementation(AndroidX.test.runner)
    androidTestImplementation(AndroidX.test.coreKtx)
}