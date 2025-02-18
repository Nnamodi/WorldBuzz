package com.roland.android.worldbuzz.data

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Result
import com.roland.android.domain.usecase.GetNewsByCategoryUseCase
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import com.roland.android.domain.usecase.GetNewsBySearchUseCase
import com.roland.android.domain.usecase.GetNewsUseCase
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverModelI
import com.roland.android.worldbuzz.ui.screens.discover.DiscoverModelII
import com.roland.android.worldbuzz.ui.screens.home.HomeModel
import com.roland.android.worldbuzz.utils.Converters.refactor
import kotlinx.coroutines.flow.MutableStateFlow

class ResponseConverter {
	fun convertHomeData(result: Result<GetNewsUseCase.Response>): State<HomeModel> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> {
				val data = result.data
				State.Success(
					HomeModel(
						trendingNews = data.trendingNews,
						recommendedNews = data.recommendedNews
							.filterNot { it in data.trendingNews }
					)
				)
			}
		}
	}

	fun convertListData(result: Result<GetNewsByCollectionUseCase.Response>): State<MutableStateFlow<PagingData<Article>>> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(result.data.articles.refactor())
		}
	}

	fun convertDiscoverDataI(result: Result<GetNewsByCategoryUseCase.Response>): State<DiscoverModelI> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(
				DiscoverModelI(
					result.data.allNews.refactor(),
					result.data.businessNews.refactor(),
					result.data.entertainmentNews.refactor(),
					result.data.healthNews.refactor()
				)
			)
		}
	}

	fun convertDiscoverDataII(result: Result<GetNewsByCategoryUseCase.Response>): State<DiscoverModelII> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(
				DiscoverModelII(
					result.data.scienceNews.refactor(),
					result.data.sportsNews.refactor(),
					result.data.techNews.refactor()
				)
			)
		}
	}

	fun convertSearchData(result: Result<GetNewsBySearchUseCase.Response>): State<MutableStateFlow<PagingData<Article>>> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(result.data.articles.refactor())
		}
	}
}