package com.roland.android.domain

import com.roland.android.domain.model.LanguageModel
import com.roland.android.domain.model.Source
import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.Collections
import com.roland.android.domain.usecase.GetNewsByCollectionUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNewsByCollectionUseCaseTest {

	private val newsRepository = mock<NewsRepository>()
	private val newsByCollectionUseCase = GetNewsByCollectionUseCase(mock(), newsRepository)

	@Test
	fun testProcess() = runTest {
		whenever(
			newsRepository.fetchNewsBySource("", "fr")
		).thenReturn(flowOf(sampleNewsPagingData))
		whenever(
			newsRepository.fetchSavedArticles()
		).thenReturn(flowOf(sampleNewsPagingData))
		whenever(
			newsRepository.fetchReadingHistory()
		).thenReturn(flowOf(sampleNewsPagingData))

		val response = newsByCollectionUseCase.process(
			GetNewsByCollectionUseCase.Request(
				collection = Collections.SavedArticles,
				source = Source(),
				language = LanguageModel.French
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