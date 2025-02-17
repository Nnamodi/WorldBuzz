package com.roland.android.domain.di

import com.roland.android.domain.usecase.GetNewsByCategoryUseCase
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.domain.usecase.GetNewsBySearchUseCase
import com.roland.android.domain.usecase.GetNewsUseCase
import com.roland.android.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainModule {
	private fun providesUseCaseConfiguration() = UseCase.Configuration(Dispatchers.IO)

	val domainModule = module {
		single { providesUseCaseConfiguration() }
		single { GetNewsUseCase(get(), get()) }
		single { GetNewsByCategoryUseCase(get(), get()) }
		single { GetNewsByCollectionUseCase(get(), get()) }
		single { GetNewsBySearchUseCase(get(), get()) }
	}
}