import kotlin.reflect.jvm.jvmName

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.example.singlekotlinlibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
//        singleVariant("debug") {
//            withJavadocJar()
//            withSourcesJar()
//        }
//        multipleVariants("allVariants") {
//            allVariants()
//            withSourcesJar()
//            withJavadocJar()
//        }
//        multipleVariants("release") {
//            allVariants()
//            withSourcesJar()
//            withJavadocJar()
//        }
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

//androidComponents {
//    beforeVariants(selector().withBuildType("debug")) { builder ->
//        builder.enable = false
//    }
//}

afterEvaluate {
    println(
        "Components: ${
            components.joinToString {
                "${it.name} - ${it::class.jvmName}"
            }
        }"
    )
    publishing {
        publications {
//            components.forEach { component ->
//                register<MavenPublication>(component.name) {
//                    groupId = "com.my-company"
//                    artifactId = "my-library-${component.name}"
//                    version = "1.0"
//                    from(components["custom"])
//                }
//            }
            register<MavenPublication>("release") {
                groupId = "com.my-company"
                artifactId = "kotlinlibrary"
                version = "1.0.0"
                from(components["release"])
            }
//            register<MavenPublication>("allVariants") {
//                groupId = "com.my-company"
//                artifactId = "my-library"
//                version = "1.0"
//                from(components["allVariants"])
//            }
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