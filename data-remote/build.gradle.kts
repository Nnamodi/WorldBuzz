import java.util.Properties

plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
}

fun getApiKey(): String {
	val properties = Properties()
	val tokenFile = project.rootProject.file("local.properties")
	properties.load(tokenFile.inputStream())
	return properties.getProperty("API_KEY")
}

android {
	namespace = "com.roland.android.data_remote"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		getByName("debug") {
			buildConfigField("String", "API_KEY", getApiKey())
		}
		getByName("release") {
			buildConfigField("String", "API_KEY", getApiKey())
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
		buildConfig = true
	}
}

dependencies {

	// android components
	implementation(libs.appcompat)
	implementation(libs.core.ktx)

	// di
	implementation(libs.koin.core)
	implementation(platform(libs.koin.bom))

	// network
	implementation(libs.moshi)
	implementation(libs.moshi.kotlin)
	implementation(libs.okHttp)
	implementation(libs.retrofit)
	implementation(libs.retrofit.moshi)

	// other modules
	implementation(project(path = ":data-repository"))
	implementation(project(path = ":domain"))

	// testing
	testImplementation(libs.coroutines)
	testImplementation(libs.junit)
	testImplementation(libs.mockito)

}