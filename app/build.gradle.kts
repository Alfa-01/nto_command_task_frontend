plugins {
    kotlinAndroid
    androidApplication
    jetbrainsKotlinSerialization version Version.Kotlin.language
    kotlinAnnotationProcessor
    id("com.google.dagger.hilt.android").version("2.51.1")
}

val packageName = "ru.myitschool.work"

android {
    namespace = packageName
    compileSdk = Version.Android.Sdk.compile

    defaultConfig {
        applicationId = packageName
        minSdk = Version.Android.Sdk.min
        targetSdk = Version.Android.Sdk.target
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = Version.Kotlin.javaSource
        targetCompatibility = Version.Kotlin.javaSource
    }

    kotlinOptions {
        jvmTarget = Version.Kotlin.jvmTarget
    }
}

dependencies {
    defaultLibrary()

    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.navigationUi)

    implementation(Dependencies.Retrofit.library)
    implementation(Dependencies.Retrofit.gsonConverter)

    implementation("com.squareup.picasso:picasso:2.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("com.google.mlkit:barcode-scanning:17.3.0")

    val cameraX = "1.3.4"
    implementation("androidx.camera:camera-core:$cameraX")
    implementation("androidx.camera:camera-camera2:$cameraX")
    implementation("androidx.camera:camera-lifecycle:$cameraX")
    implementation("androidx.camera:camera-view:$cameraX")
    implementation("androidx.camera:camera-mlkit-vision:1.4.0-rc04")

    val hilt = "2.51.1"
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
}

kapt {
    correctErrorTypes = true
}
