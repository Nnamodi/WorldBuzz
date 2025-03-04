package com.roland.android.domain

import com.roland.android.domain.repository.NewsRepository
import com.roland.android.domain.usecase.GetNewsBySearchUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetNewsBySearchUseCaseTest {

	private val newsRepository = mock<NewsRepository>()
	private val newsBySearchUseCase = GetNewsBySearchUseCase(mock(), newsRepository)

	@Test
	fun testProcess() = runTest {
		whenever(
			newsRepository.searchNews("fisherman", ",", ",")
		).thenReturn(flowOf(sampleNewsPagingData))

		val response = newsBySearchUseCase.process(
			GetNewsBySearchUseCase.Request(
				query = "fisherman",
				category = "",
				sources = listOf("")
			)
		).first()
		assertEquals(
			GetNewsBySearchUseCase.Response(
				articles = sampleNewsPagingData
			),
			response
		)
	}

}