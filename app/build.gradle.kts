plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.compose.compiler)
    // Add this line:
//    id("org.jetbrains.kotlin.plugin.compose") version "1.5.1" // Version should match your kotlinCompilerExtensionVersion
}

android {
    namespace = "com.void_main.chainex"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.void_main.chainex"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //razorpay
    implementation ("com.razorpay:checkout:1.6.33")
    //navigation
    implementation (libs.androidx.navigation.compose)
    //activity
    implementation(libs.androidx.activity.compose.v180alpha07)
    //lottie
    implementation("com.airbnb.android:lottie-compose:6.1.0")
    //gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
    //maps
    implementation("com.google.maps.android:maps-compose:6.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.wallet)
    implementation(libs.barcode.scanning)
    implementation(libs.play.services.code.scanner)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    //camera
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    implementation("com.google.guava:guava:31.1-android")

    //permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.32.0")
    //web3
//    implementation("com.walletconnect:android-sdk:1.11.0")
//    implementation("org.web3j:core:4.9.8")
//    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    //
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}