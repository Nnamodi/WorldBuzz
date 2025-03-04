package com.roland.android.data_repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.roland.android.data_repository.data_source.local.LocalNewsDataSource
import com.roland.android.data_repository.data_source.remote.RemoteNewsDataSource
import com.roland.android.data_repository.utils.Constants.INITIAL_PAGE
import com.roland.android.domain.model.Article
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsByCategoryPagingSource(
	private val selectedCategories: String,
	private val languageCode: String
) : PagingSource<Int, Article>(), KoinComponent {
	private val remoteNewsDataSource by inject<RemoteNewsDataSource>()

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
		return try {
			val currentPage = params.key ?: INITIAL_PAGE
			val news = remoteNewsDataSource.fetchNewsByCategory(selectedCategories, languageCode, currentPage)
			LoadResult.Page(
				data = news,
				prevKey = if (currentPage == 1) null else currentPage - 1,
				nextKey = if (news.isEmpty()) null else currentPage + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(throwable = e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}

class NewsBySourcePagingSource(
	private val source: String,
	private val languageCode: String
) : PagingSource<Int, Article>(), KoinComponent {
	private val remoteNewsDataSource by inject<RemoteNewsDataSource>()

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
		return try {
			val currentPage = params.key ?: INITIAL_PAGE
			val news = remoteNewsDataSource.fetchNewsBySource(source, languageCode, currentPage)
			LoadResult.Page(
				data = news,
				prevKey = if (currentPage == 1) null else currentPage - 1,
				nextKey = if (news.isEmpty()) null else currentPage + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(throwable = e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}

class SearchedNewsPagingSource(
	private val query: String,
	private val category: String,
	private val sources: String
) : PagingSource<Int, Article>(), KoinComponent {
	private val remoteNewsDataSource by inject<RemoteNewsDataSource>()

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
		return try {
			val currentPage = params.key ?: INITIAL_PAGE
			val news = remoteNewsDataSource.searchNews(query, category, sources, currentPage)
			LoadResult.Page(
				data = news,
				prevKey = if (currentPage == 1) null else currentPage - 1,
				nextKey = if (news.isEmpty()) null else currentPage + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(throwable = e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}

class SavedArticlesPagingSource : PagingSource<Int, Article>(), KoinComponent {
	private val localNewsDataSource by inject<LocalNewsDataSource>()

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
		return try {
			val currentPage = params.key ?: INITIAL_PAGE
			val news = localNewsDataSource.fetchSavedArticles()
			LoadResult.Page(
				data = news,
				prevKey = if (currentPage == 1) null else currentPage - 1,
				nextKey = if (news.isEmpty()) null else currentPage + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(throwable = e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}

class ReadingHistoryPagingSource : PagingSource<Int, Article>(), KoinComponent {
	private val localNewsDataSource by inject<LocalNewsDataSource>()

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
		return try {
			val currentPage = params.key ?: INITIAL_PAGE
			val news = localNewsDataSource.fetchReadingHistory()
			LoadResult.Page(
				data = news,
				prevKey = if (currentPage == 1) null else currentPage - 1,
				nextKey = if (news.isEmpty()) null else currentPage + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(throwable = e)
		}
	}

	override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}