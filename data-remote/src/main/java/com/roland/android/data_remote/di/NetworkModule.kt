package com.roland.android.data_remote.di

import com.roland.android.data_remote.BuildConfig
import com.roland.android.data_remote.data_source.RemoteNewsDataSourceImpl
import com.roland.android.data_remote.network.service.NewsService
import com.roland.android.data_remote.utils.Constant.BASE_URL
import com.roland.android.data_repository.data_source.remote.RemoteNewsDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
	private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
		.readTimeout(60, TimeUnit.SECONDS)
		.connectTimeout(60, TimeUnit.SECONDS)
		.addInterceptor(
			Interceptor { chain: Interceptor.Chain ->
				val request = chain.request()
					.newBuilder()
					.header("Authorization", "Bearer${BuildConfig.API_KEY}")
					.build()
				chain.proceed(request)
			}
		).build()

	private fun provideMoshi(): Moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()

	private fun provideRetrofit(
		okHttpClient: OkHttpClient,
		moshi: Moshi
	): Retrofit = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.client(okHttpClient)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.build()

	private fun provideNewsService(retrofit: Retrofit): NewsService {
		return retrofit.create(NewsService::class.java)
	}

	val networkModule = module {
		single { provideOkHttpClient() }
		single { provideMoshi() }
		single { provideRetrofit(get(), get()) }
		single { provideNewsService(get()) }
		factory<RemoteNewsDataSource> { RemoteNewsDataSourceImpl(get()) }
	}
}