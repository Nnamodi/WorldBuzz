package com.roland.android.worldbuzz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverViewModel
import com.roland.android.worldbuzz.ui.screens.home.HomeViewModel
import com.roland.android.worldbuzz.ui.screens.list.ListScreen
import com.roland.android.worldbuzz.ui.screens.list.ListViewModel
import com.roland.android.worldbuzz.ui.screens.search.SearchScreen
import com.roland.android.worldbuzz.ui.screens.search.SearchViewModel
import com.roland.android.worldbuzz.utils.AnimationDirection
import com.roland.android.worldbuzz.utils.Converters.toCollectionDetails
import com.roland.android.worldbuzz.utils.animatedComposable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppRoute(navController: NavHostController) {
	val navActions = NavActions(navController)
	val homeViewModel: HomeViewModel = koinViewModel()
	val discoverViewModel: DiscoverViewModel = koinViewModel()
	val listViewModel: ListViewModel = koinViewModel()
	val searchViewModel: SearchViewModel = koinViewModel()

	NavHost(
		navController = navController,
		startDestination = AppRoute.StartScreens.route
	) {
		startScreenRoute(
			navActions = navActions,
			homeViewModel = homeViewModel,
			discoverViewModel = discoverViewModel
		)
		animatedComposable(AppRoute.ListScreen.route) { backStackEntry ->
			val collectionJson = backStackEntry.arguments?.getString("collection") ?: ""
			val collectionDetails = collectionJson.toCollectionDetails()
			LaunchedEffect(true) {
				listViewModel.fetchNewsByCollection(collectionDetails)
			}

			ListScreen(
				state = listViewModel.articles,
				collectionDetails = collectionDetails,
				action = listViewModel::action,
				navigate = navActions::navigate
			)
		}
		animatedComposable(
			route = AppRoute.SearchScreen.route,
			animationDirection = AnimationDirection.UpDown
		) {
			SearchScreen(
				uiState = searchViewModel.searchUiState,
				action = searchViewModel::actions,
				navigate = navActions::navigate
			)
		}
	}
}