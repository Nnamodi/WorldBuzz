plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.room)
}

android {
	namespace = "com.roland.android.data_local"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		room {
			schemaDirectory(path = "$projectDir/schemas")
		}

		testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
}

dependencies {

	// android components
	implementation(libs.appcompat)
	implementation(libs.core.ktx)

	// di
	implementation(libs.koin.core)
	implementation(platform(libs.koin.bom))

	// other modules
	implementation(project(path = ":data-repository"))
	implementation(project(path = ":domain"))

	// persistence
	implementation(libs.datastore)
	implementation(libs.room.ktx)
	implementation(libs.room.runtime)
	ksp(libs.room.compiler)

	// testing
	testImplementation(libs.coroutines)
	testImplementation(libs.junit)
	testImplementation(libs.mockito)

}