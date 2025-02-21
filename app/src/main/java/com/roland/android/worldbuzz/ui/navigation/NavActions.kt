package com.roland.android.worldbuzz.ui.navigation

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {
	fun navigate(screen: Screens) {
		when (screen) {
			Screens.HomeScreen -> navigateToHomeScreen()
			Screens.DiscoverScreen -> navigateToDiscoverScreen()
			Screens.ProfileScreen -> navigateToProfileScreen()
			is Screens.DetailsScreen -> navigateToDetailsScreen(screen.articleJson)
			Screens.SearchScreen -> navigateToSearchScreen()
			is Screens.ListScreen -> navigateToListScreen(screen.collectionJson)
			Screens.CategoryScreen -> navigateToCategoryScreen()
			Screens.SourcesScreen -> navigateToSourcesScreen()
			Screens.SettingsScreen -> navigateToSettingsScreen()
			Screens.Back -> navController.navigateUp()
		}
	}

	private fun navigateToHomeScreen() {
		navController.navigate(AppRoute.HomeScreen.route)
	}

	private fun navigateToDiscoverScreen() {
		navController.navigate(AppRoute.DiscoverScreen.route)
	}

	private fun navigateToProfileScreen() {
		navController.navigate(AppRoute.ProfileScreen.route)
	}

	private fun navigateToDetailsScreen(articleJson: String) {
		navController.navigate(
			AppRoute.DetailsScreen.routeWithArticle(articleJson)
		)
	}

	private fun navigateToSearchScreen() {
		navController.navigate(AppRoute.SearchScreen.route)
	}

	private fun navigateToListScreen(collectionJson: String) {
		navController.navigate(
			AppRoute.ListScreen.routeWithCollection(collectionJson)
		)
	}

	private fun navigateToCategoryScreen() {
		navController.navigate(AppRoute.CategoryScreen.route)
	}

	private fun navigateToSourcesScreen() {
		navController.navigate(AppRoute.SourcesScreen.route)
	}

	private fun navigateToSettingsScreen() {
		navController.navigate(AppRoute.SettingsScreen.route)
	}
}

sealed class AppRoute(val route: String) {
	data object HomeScreen : AppRoute("home_screen")
	data object DiscoverScreen : AppRoute("discover_screen")
	data object ProfileScreen : AppRoute("profile_screen")
	data object DetailsScreen : AppRoute("details_screen/{article}") {
		fun routeWithArticle(articleJson: String) = String.format("details_screen/%s", articleJson)
	}
	data object SearchScreen : AppRoute("search_screen")
	data object ListScreen : AppRoute("list_screen/{collection}") {
		fun routeWithCollection(collectionJson: String) = String.format("list_screen/%s", collectionJson)
	}
	data object CategoryScreen : AppRoute("category_screen")
	data object SourcesScreen : AppRoute("sources_screen")
	data object SettingsScreen : AppRoute("settings_screen")
}

sealed class Screens {
	data object HomeScreen : Screens()
	data object DiscoverScreen : Screens()
	data object ProfileScreen : Screens()
	data class DetailsScreen(val articleJson: String) : Screens()
	data object SearchScreen : Screens()
	data class ListScreen(val collectionJson: String) : Screens()
	data object CategoryScreen : Screens()
	data object SourcesScreen : Screens()
	data object SettingsScreen : Screens()
	data object Back : Screens()
}