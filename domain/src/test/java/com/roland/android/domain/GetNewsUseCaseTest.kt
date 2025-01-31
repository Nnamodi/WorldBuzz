package com.roland.android.domain

import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.GetNewsUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNewsUseCaseTest {

	private val newsRepository = mock<NewsRepository>()
	private val newsUseCase = GetNewsUseCase(mock(), newsRepository)

	@Test
	fun testProcess() = runTest {
		whenever(
			newsRepository.fetchTrendingNews("general", "", "fr")
		).thenReturn(flowOf(sampleNewsData))
		whenever(
			newsRepository.fetchRecommendedNews("general", "fr")
		).thenReturn(flowOf(sampleNewsData))

		val response = newsUseCase.process(
			GetNewsUseCase.Request(
				categoryModels = CategoryModel.entries,
				sources = "",
				languageCode = LanguageModel.French.code
			)
		).first()
		assertEquals(
			GetNewsUseCase.Response(
				trendingNews = sampleNewsData,
				recommendedNews = sampleNewsData
			),
			response
		)
	}

}