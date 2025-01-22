package com.roland.android.domain.di

import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.domain.usecase.GetNewsUseCase
import org.koin.dsl.module

object DomainModule {
	val domainModule = module {
		single { GetNewsUseCase(get(), get()) }
		single { GetNewsByCollectionUseCase(get(), get()) }
	}
}