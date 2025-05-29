plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.ecommerceapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ecommerceapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {viewBinding = true}
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
}

dependencies {
    implementation(libs.billing)
    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        // Retrofit
        implementation(libs.retrofit)
        implementation(libs.retrofitGson)

        // Glide
        implementation(libs.glide)
        kapt(libs.glideCompiler)

        // QR Code Scanner (ZXing Android)
        implementation(libs.zxing.android.embedded)
        implementation(libs.core)
        //implementation(libs.zxing)
        //implementation("me.dm7.barcodescanner:zxing:1.9.13")

        // Moshi
        implementation("com.squareup.retrofit2:converter-moshi:2.8.1")

        // ViewModel
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.cardview:cardview:1.0.0")
        implementation("com.github.bumptech.glide:glide:4.12.0")
        kapt("com.github.bumptech.glide:compiler:4.12.0")

        implementation("com.google.android.material:material:1.11.0")

        // Stripe
        implementation("com.stripe:stripe-android:21.15.0")



    }

}