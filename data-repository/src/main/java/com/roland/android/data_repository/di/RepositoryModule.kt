package com.roland.android.data_repository.di

import android.content.Context
import com.roland.android.data_repository.repository.NewsRepositoryImpl
import com.roland.android.data_repository.repository.SettingsRepositoryImpl
import com.roland.android.data_repository.repository.UtilityRepositoryImpl
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.repository.SettingsRepository
import com.roland.android.domain.repository.UtilityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object RepositoryModule {
	private fun provideCoroutineScope() = CoroutineScope(Dispatchers.IO)

	val repositoryModule = module {
		single { provideCoroutineScope() }
		factory<NewsRepository> { NewsRepositoryImpl(get(), get(), get()) }
		factory<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
		factory<UtilityRepository> { UtilityRepositoryImpl(get(), get(), get<Context>().applicationContext) }
	}
}