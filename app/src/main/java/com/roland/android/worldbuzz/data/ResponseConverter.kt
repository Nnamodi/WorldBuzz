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

	fun convertListData(result: Result<GetNewsByCollectionUseCase.Response>): State<PagingData<Article>> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(result.data.articles)
		}
	}

	fun convertDiscoverDataI(result: Result<GetNewsByCategoryUseCase.Response>): State<DiscoverModelI> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(
				DiscoverModelI(
					result.data.allNews,
					result.data.businessNews,
					result.data.entertainmentNews,
					result.data.healthNews
				)
			)
		}
	}

	fun convertDiscoverDataII(result: Result<GetNewsByCategoryUseCase.Response>): State<DiscoverModelII> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(
				DiscoverModelII(
					result.data.scienceNews,
					result.data.sportsNews,
					result.data.techNews
				)
			)
		}
	}

	fun convertSearchData(result: Result<GetNewsBySearchUseCase.Response>): State<PagingData<Article>> {
		return when (result) {
			is Result.Error -> State.Error(result.exception.localizedMessage.orEmpty())
			is Result.Success -> State.Success(result.data.articles)
		}
	}
}