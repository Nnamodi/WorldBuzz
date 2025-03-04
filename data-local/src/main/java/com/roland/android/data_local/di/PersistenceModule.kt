package com.roland.android.data_local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.roland.android.data_local.data_source.LocalNewsDataSourceImpl
import com.roland.android.data_local.data_source.SettingsDataSourceImpl
import com.roland.android.data_local.data_source.UtilityDataSourceImpl
import com.roland.android.data_local.database.AppDatabase
import com.roland.android.data_local.database.HistoryDao
import com.roland.android.data_local.database.NewsDao
import com.roland.android.data_local.database.SavedNewsDao
import com.roland.android.data_local.database.SourceDao
import com.roland.android.data_local.datastore.SettingsStore
import com.roland.android.data_local.datastore.SubscribedCategoryStore
import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.data_repository.data_source.local.SettingsDataSource
import com.roland.android.data_repository.data_source.local.UtilityDataSource
import org.koin.dsl.module

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

object PersistenceModule {

	private fun provideAppDatabase(context: Context): AppDatabase {
		return Room.databaseBuilder(
			context = context,
			klass = AppDatabase::class.java,
			name = "app-database"
		).build()
	}

	private fun provideDataStore(context: Context): DataStore<Preferences> {
		return context.datastore
	}

	private fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
		return appDatabase.newsDao()
	}

	private fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
		return appDatabase.historyDao()
	}

	private fun provideSavedNewsDao(appDatabase: AppDatabase): SavedNewsDao {
		return appDatabase.savedNewsDao()
	}

	private fun provideSourceDao(appDatabase: AppDatabase): SourceDao {
		return appDatabase.sourceDao()
	}

	val persistenceModule = module {
		single { provideAppDatabase(get<Context>().applicationContext) }
		single { provideDataStore(get<Context>().applicationContext) }
		single { provideNewsDao(get()) }
		single { provideHistoryDao(get()) }
		single { provideSavedNewsDao(get()) }
		single { provideSourceDao(get()) }
		single { SettingsStore(get()) }
		single { SubscribedCategoryStore(get()) }
		factory<LocalNewsDataSource> { LocalNewsDataSourceImpl(get(), get(), get(), get(), get(), get()) }
		factory<SettingsDataSource> { SettingsDataSourceImpl(get()) }
		factory<UtilityDataSource> { UtilityDataSourceImpl(get(), get(), get(), get()) }
	}

}