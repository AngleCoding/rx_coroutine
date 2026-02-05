plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    id("maven-publish")
}

group = "com.beijing.angle.rx_coroutine"

android {
    namespace = "com.beijing.angle.rx_coroutine"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    viewBinding {
        enable = true
    }

    ksp {
        arg("rxhttp_rxjava", "3.1.12")
    }


    ksp {
        arg("rxhttp_package", "com.beijing.angle.rx_coroutine.rxhttp")
    }

    sourceSets {
        getByName("main") {
            res {
                srcDirs("src\\main\\res", "src\\main\\res")
            }
        }
    }


}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.fragment)
    implementation(libs.lifecycle)
    implementation(libs.lifecycle.runtime)
    implementation(libs.livedata)
    implementation(libs.livedata.savedstate)
    implementation(libs.livedata)

    api(libs.okhttp3)
    api(libs.rxhttp)
    ksp(libs.rxhttp.compiler)

    api(libs.rxjava)
    api(libs.rxandroid)

    api(libs.androidAutosize)

    api(libs.utilcodex)
    api(libs.rxbinding)
    api(libs.rxviewpage)
    api(libs.rxrecyclerview)

    api(libs.flycoTabLayout)
    api(libs.xpopup)

    api(libs.picture)
    api(libs.appupdate)

    api(libs.pickerView)
    api(libs.adapter)


    api(libs.datastore)
    api(libs.preferences)



}

afterEvaluate {
    publishing {
        publications {
            // 创建Maven发布 "release"
            create<MavenPublication>("release") {
                // 应用release构建组件
                from(components["release"])

                // 自定义发布属性
                groupId = "com.beijing.angle.rx_coroutine"
                artifactId = "rx_coroutine"
                version = "v1.0.0"
            }
        }
    }
}