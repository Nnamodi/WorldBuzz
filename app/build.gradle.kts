   plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
}

android {
	namespace = "com.roland.android.worldbuzz"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.roland.android.worldbuzz"
		minSdk = 24
		targetSdk = 34
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
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

	// android-compose
	implementation(libs.appcompat)
	implementation(libs.core.ktx)
	implementation(libs.compose.activity)
	implementation(libs.compose.coil)
	implementation(libs.compose.navigation)
	implementation(libs.compose.ui)
	implementation(libs.compose.ui.graphics)
	implementation(libs.compose.ui.tooling.preview)
	implementation(platform(libs.compose.bom))
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.splashscreen)

	// di
	implementation(libs.koin.android)
	implementation(libs.koin.compose)
	implementation(libs.koin.core)
	implementation(platform(libs.koin.bom))

	// material design
	implementation(libs.material3)
	implementation(libs.material.icons)

	// other modules
	implementation(project(path = ":data-local"))
	implementation(project(path = ":data-remote"))
	implementation(project(path = ":data-repository"))
	implementation(project(path = ":domain"))
//	implementation(project(path = ":webview"))

	// paging
	implementation(libs.paging.compose)

	// testing
	testImplementation(libs.junit)
	androidTestImplementation(libs.compose.ui.test.junit4)
	androidTestImplementation(libs.espresso.core)
	androidTestImplementation(platform(libs.compose.bom))
	debugImplementation(libs.compose.ui.test.manifest)
	debugImplementation(libs.compose.ui.tooling)

}