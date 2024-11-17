plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.farmacia_fameza"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.farmacia_fameza"
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Implementacion Spinner
    implementation ("androidx.appcompat:appcompat:1.4.1") // Verifica si hay una versión más reciente
    implementation ("com.google.android.material:material:1.4.0")// Si usas Material Design

    implementation ("com.makeramen:roundedimageview:2.3.0")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.legacy.support.v4)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //Implementacion Carga de Imagenes
    implementation ("com.squareup.picasso:picasso:2.8")

    //Implementacion Generar PDF Kardex
    implementation ("com.itextpdf:itext7-core:7.2.3")

    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}