@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.android.library")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        name = "KMPShared"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "12.0"
        podfile = project.file("../kmp-app-ios-scaffold/Podfile")
        framework {
            baseName = "KMPShared"
            isStatic = true // SwiftUI preview requires dynamic framework
        }
        extraSpecAttributes["swift_version"] = "\"5.0\""
    }

    sourceSets {
        commonMain.dependencies {
            // put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies { }
        getByName("androidUnitTest").dependencies {
            implementation(libs.androidx.test.junit)
        }
        iosMain.dependencies { }
    }

    dependencies {
        testImplementation(libs.junit)
    }
}

android {
    namespace = "com.github.app"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
