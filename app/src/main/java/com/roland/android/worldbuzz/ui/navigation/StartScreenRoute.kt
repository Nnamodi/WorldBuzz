package com.roland.android.worldbuzz.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverScreen
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverViewModel
import com.roland.android.worldbuzz.ui.screens.home.HomeScreen
import com.roland.android.worldbuzz.ui.screens.home.HomeViewModel
import com.roland.android.worldbuzz.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.startScreenRoute(
	navActions: NavActions,
	homeViewModel: HomeViewModel,
	discoverViewModel: DiscoverViewModel
) {
	navigation(
		startDestination = AppRoute.HomeScreen.route,
		route = AppRoute.StartScreens.route
	) {
		composable(AppRoute.HomeScreen.route) {
			HomeScreen(
				uiState = homeViewModel.homeUiState,
				retry = homeViewModel::retry,
				navigate = navActions::navigate
			)
		}
		composable(AppRoute.DiscoverScreen.route) {
			DiscoverScreen(
				uiState = discoverViewModel.discoverUiState,
				retry = discoverViewModel::retry,
				navigate = navActions::navigate
			)
		}
		composable(AppRoute.ProfileScreen.route) {
			ProfileScreen(navActions::navigate)
		}
	}
}

@Composable
fun PaddingValues.paddingValues(bottomPadding: Dp): PaddingValues {
	val layoutDirection = LocalLayoutDirection.current
	return PaddingValues(
		start = calculateStartPadding(layoutDirection),
		top = calculateTopPadding(),
		end = calculateEndPadding(layoutDirection),
		bottom = calculateBottomPadding() + bottomPadding
	)
}