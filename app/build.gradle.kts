plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.commonwealthu.mindeye"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.commonwealthu.mindeye"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

}

dependencies {
    //implementation(libs.material3)
    //implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    //implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.anychart.android)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}