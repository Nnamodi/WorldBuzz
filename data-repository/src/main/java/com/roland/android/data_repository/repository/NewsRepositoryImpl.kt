package com.roland.android.data_repository.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.data_repository.data_source.remote.RemoteNewsDataSource
import com.roland.android.data_repository.paging.NewsByCategoryPagingSource
import com.roland.android.data_repository.paging.NewsBySourcePagingSource
import com.roland.android.data_repository.paging.ReadingHistoryPagingSource
import com.roland.android.data_repository.paging.SavedArticlesPagingSource
import com.roland.android.data_repository.paging.SearchedNewsPagingSource
import com.roland.android.data_repository.utils.Constants.MAX_PAGE_SIZE
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.util.Constant.SEPARATOR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
	private val localNewsDataSource: LocalNewsDataSource,
	private val remoteNewsDataSource: RemoteNewsDataSource,
	private val scope: CoroutineScope
) : NewsRepository {

	override fun fetchTrendingNews(
		selectedCategories: String,
		selectedSources: String,
		languageCode: String
	): Flow<List<Article>> {
		val categories = selectedCategories.split(SEPARATOR)
		return try {
			val remoteData = remoteNewsDataSource.fetchTrendingNews(
				selectedCategories,
				selectedSources,
				languageCode
			)
			remoteData.map {
				localNewsDataSource.clearCachedNews()
				localNewsDataSource.saveTrendingNews(categories, it)
			}
			remoteData
		} catch (e: Exception) {
			localNewsDataSource.fetchTrendingNews(categories)
		}
	}

	override fun fetchRecommendedNews(
		category: String,
		languageCode: String
	): Flow<List<Article>> {
		return try {
			val remoteData = remoteNewsDataSource.fetchRecommendedNews(category, languageCode)
			remoteData.map { localNewsDataSource.saveRecommendedNews(category, it) }
			remoteData
		} catch (e: Exception) {
			localNewsDataSource.fetchRecommendedNews(category)
		}
	}

	override fun fetchNewsByCategory(
		category: String,
		languageCode: String
	): Flow<PagingData<Article>> {
		return pagingDataFlow {
			NewsByCategoryPagingSource(category, languageCode)
		}
	}

	override fun fetchNewsBySource(
		source: String,
		languageCode: String
	): Flow<PagingData<Article>> {
		return pagingDataFlow {
			NewsBySourcePagingSource(source, languageCode)
		}
	}

	override fun searchNews(
		query: String,
		categories: String,
		sources: String,
		languageCode: String
	): Flow<PagingData<Article>> {
		return pagingDataFlow {
			SearchedNewsPagingSource(query, categories, sources, languageCode)
		}
	}

	override fun fetchAllSources(): Flow<List<SourceDetail>> {
		return combine(
			localNewsDataSource.fetchAllSources(),
			remoteNewsDataSource.fetchAllSources()
		) { localNewsSources, remoteNewsSources ->
			localNewsSources.ifEmpty {
				localNewsDataSource.saveAllSources(remoteNewsSources)
				remoteNewsSources
			}
		}
	}

	override fun fetchSourceDetails(source: Source): Flow<SourceDetail> {
		return localNewsDataSource.fetchSourceDetails(source)
	}

	override fun fetchSavedArticles(): Flow<PagingData<Article>> {
		return pagingDataFlow { SavedArticlesPagingSource() }
	}

	override fun fetchReadingHistory(): Flow<PagingData<Article>> {
		return pagingDataFlow { ReadingHistoryPagingSource() }
	}

	override fun fetchSubscribedSources(): Flow<List<Source>> {
		return localNewsDataSource.fetchSubscribedSources()
	}

	override fun fetchSubscribedCategories(): Flow<List<CategoryModel>> {
		return localNewsDataSource.fetchSubscribedCategories()
	}

	private fun pagingDataFlow(
		factory: () -> PagingSource<Int, Article>
	) = Pager(
		config = PagingConfig(
			pageSize = MAX_PAGE_SIZE,
			prefetchDistance = MAX_PAGE_SIZE / 2
		),
		pagingSourceFactory = factory
	).flow
		.distinctUntilChanged()
		.cachedIn(scope)
}