package com.roland.android.worldbuzz.di

import com.roland.android.worldbuzz.data.ResponseConverter
import com.roland.android.worldbuzz.ui.screens.detail.DetailsViewModel
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverViewModel
import com.roland.android.worldbuzz.ui.screens.following.FollowingViewModel
import com.roland.android.worldbuzz.ui.screens.home.HomeViewModel
import com.roland.android.worldbuzz.ui.screens.list.ListViewModel
import com.roland.android.worldbuzz.ui.screens.search.SearchViewModel
import com.roland.android.worldbuzz.ui.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
	val appModule = module {
		single { ResponseConverter() }
		viewModel { DetailsViewModel() }
		viewModel { DiscoverViewModel() }
		viewModel { FollowingViewModel() }
		viewModel { HomeViewModel() }
		viewModel { ListViewModel() }
		viewModel { SearchViewModel() }
		viewModel { SettingsViewModel() }
	}
}