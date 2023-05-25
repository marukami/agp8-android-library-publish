# agp8-android-library-publish

An example repo using AGP8 publishing

* Show how Kotlin always create software components
* Show how to use AGP with Kotlin plugin
* Show how to create a multipleVariants software components for single and multi-variant projects

You can find the Android documentation here https://developer.android.com/build/publish-library

First note is that Kotlin Android plugin will generate all variants regardless if you configure that
variant in the `android.publishing` block. So, you will always all possible variants for a library
or application module.

Does that mean Kotlin Android plugin is ignoring the AGP publishing? No, it will just create all
possible variants regardless of what you configure in `android.publishing`. So, an
empty `android.publishing` block will result in a maven artifact that contains the
`.AAR`, `.module` and `.pom`

How do you add source and javadoc. The easiest way is to use `android.publishing`. You can configure
each variant. Tho the drawback to split variants approach is that you will have two multiple 
artifacts instead of a single artifact.

```kotlin
android {
    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
        singleVariant("debug") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}
```

## Single Variant

Since the Kotlin plugin create a SoftwareComponent per variant. We could use the Kotlin generated
SoftwareComponent, tho this does confusienly lead to  


```kotlin
androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}
```

You will need to enable build type fallback if you also consume the module locally.
```kotlin
android {
    buildTypes {
        getByName("debug") {
            matchingFallbacks += "release"
        }
    }
}
```

## Multiple Variants module

A multiple variants can contain all build types and flavours or a single build type of a flavour.
For here we are assuming we don't care about KMM and just want to publish a typical Android library.

To publish all debug and release use

```kotlin
android {
    publishing {
        multipleVariants("allVariants") {
            allVariants()
            withSourcesJar()
            withJavadocJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("allVariants") {
                groupId = "com.my-company"
                artifactId = "my-library"
                version = "1.0"
                from(components["allVariants"])
            }
        }
    }
}
```

To only publish a single build type use

```kotlin
android {
    publishing {
        register<MavenPublication>("release") {
            groupId = "com.my-company"
            artifactId = "my-library"
            version = "1.0"
            from(components["release"])
        }
    }
}
afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.my-company"
                artifactId = "my-library"
                version = "1.0"
                from(components["release"])
            }
        }
    }
}
```

The kotlin Gradle plugin always creates SoftwareComponent that are KMM
even when you only have Android as a runtime target.

```kotlin
android {
    publishing {
        multipleVariants("custom") {
            includeBuildTypeValues("release")
            withSourcesJar()
            withJavadocJar()
        }
    }
}
```