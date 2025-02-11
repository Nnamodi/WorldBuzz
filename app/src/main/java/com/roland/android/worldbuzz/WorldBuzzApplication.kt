package com.roland.android.worldbuzz

import android.app.Application
import com.roland.android.data_local.di.PersistenceModule.persistenceModule
import com.roland.android.data_remote.di.NetworkModule.networkModule
import com.roland.android.data_repository.di.RepositoryModule.repositoryModule
import com.roland.android.domain.di.DomainModule.domainModule
import com.roland.android.worldbuzz.di.AppModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WorldBuzzApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@WorldBuzzApplication)
			androidLogger(Level.INFO)
			modules(
				appModule,
				domainModule,
				repositoryModule,
				networkModule,
				persistenceModule
			)
		}
	}
}