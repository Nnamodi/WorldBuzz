package com.roland.android.domain

import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.CategorySet
import com.roland.android.domain.usecase.GetNewsByCategoryUseCase
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNewsByCategoryUseCaseTest {

	private val newsRepository = mock<NewsRepository>()
	private val newsByCategoryUseCase = GetNewsByCategoryUseCase(mock(), newsRepository)

	@Test
	fun testProcess() = runTest {
		whenever(
			newsRepository.fetchNewsByCategory("", "fr")
		).thenReturn(flowOf(sampleNewsPagingData))
		whenever(
			newsRepository.fetchNewsByCategory("", "fr")
		).thenReturn(flowOf(sampleNewsPagingData))
		whenever(
			newsRepository.fetchNewsByCategory("", "fr")
		).thenReturn(flowOf(sampleNewsPagingData))

		val response = newsByCategoryUseCase.process(
			GetNewsByCategoryUseCase.Request(
				categorySet = CategorySet.Second,
				languageCode = LanguageModel.French.code
			)
		).first()
		assertEquals(
			GetNewsByCollectionUseCase.Response(
				articles = sampleNewsPagingData
			),
			response
		)
	}

}