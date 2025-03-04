package com.roland.android.domain.usecase

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.CategoryModel.All
import com.roland.android.domain.model.CategoryModel.Business
import com.roland.android.domain.model.CategoryModel.Entertainment
import com.roland.android.domain.model.CategoryModel.Health
import com.roland.android.domain.model.CategoryModel.Science
import com.roland.android.domain.model.CategoryModel.Sports
import com.roland.android.domain.model.CategoryModel.Technology
import com.roland.android.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetNewsByCategoryUseCase(
	configuration: Configuration,
	private val newsRepository: NewsRepository // for easy mocking during unit test
) : UseCase<GetNewsByCategoryUseCase.Request, GetNewsByCategoryUseCase.Response>(configuration) {

	override fun process(request: Request): Flow<Response> {
		return when (request.categorySet) {
			CategorySet.First -> {
				combine(
					newsRepository.fetchNewsByCategory(All.category, request.languageCode),
					newsRepository.fetchNewsByCategory(Business.category, request.languageCode),
					newsRepository.fetchNewsByCategory(Entertainment.category, request.languageCode),
					newsRepository.fetchNewsByCategory(Health.category, request.languageCode)
				) { all, business, entertainment, health ->
					Response(all, business, entertainment, health)
				}
			}
			CategorySet.Second -> {
				combine(
					newsRepository.fetchNewsByCategory(Science.category, request.languageCode),
					newsRepository.fetchNewsByCategory(Sports.category, request.languageCode),
					newsRepository.fetchNewsByCategory(Technology.category, request.languageCode)
				) { science, sports, tech ->
					Response(scienceNews = science, sportsNews = sports, techNews = tech)
				}
			}
		}
	}

	data class Request(
		val categorySet: CategorySet,
		val languageCode: String
	) : UseCase.Request

	data class Response(
		val allNews: PagingData<Article> = PagingData.empty(),
		val businessNews: PagingData<Article> = PagingData.empty(),
		val entertainmentNews: PagingData<Article> = PagingData.empty(),
		val healthNews: PagingData<Article> = PagingData.empty(),
		val scienceNews: PagingData<Article> = PagingData.empty(),
		val sportsNews: PagingData<Article> = PagingData.empty(),
		val techNews: PagingData<Article> = PagingData.empty()
	) : UseCase.Response

}

enum class CategorySet { First, Second }