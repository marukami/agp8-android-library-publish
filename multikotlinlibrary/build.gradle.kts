import org.gradle.api.plugins.internal.DefaultAdhocSoftwareComponent

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}


android {
    namespace = "com.example.kotlinlibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        multipleVariants("release") {
            allVariants()
            withSourcesJar()
            withJavadocJar()
        }
    }
    flavorDimensions += "iceCream"
    productFlavors {
        register("chocolate")
        register("vanilla")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

afterEvaluate {
    println("Components: ${components.joinToString { it.name }}")
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.my-company"
                artifactId = "flavoured-kotlinlibrary"
                version = "1.0"
                from(components["release"])
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}