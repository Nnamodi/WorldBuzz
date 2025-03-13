package com.roland.android.worldbuzz.data

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail
import com.roland.android.worldbuzz.utils.Converters.refactor

private val sampleNews = Article(
	source = Source(name = "CNN"),
	title = "What training do Volley Ball players need?",
	author = "Jones",
	publishedAt = "Feb 23, 2025"
)

val sampleNewsData = listOf(
	sampleNews, sampleNews,
	sampleNews, sampleNews,
	sampleNews, sampleNews,
	sampleNews, sampleNews,
	sampleNews, sampleNews
)

val sampleNewsPagingData = PagingData.from(sampleNewsData).refactor()

val sampleNewsSource = listOf(
	SourceDetail(name = "The Business Of Fashion"), SourceDetail(name = "CNN"),
	SourceDetail(name = "The New York Times"), SourceDetail(name = "Al jazeera"),
	SourceDetail(name = "Consumer Reports"), SourceDetail(name = "Channels"),
	SourceDetail(name = "Financial Times"), SourceDetail(name = "Fox News"),
	SourceDetail(name = "The Atlantic"), SourceDetail(name = "NTA"),
	SourceDetail(name = "Bloomberg"), SourceDetail(name = "Noahpinion")
)

val sampleNewsSourceI = listOf(
	Source(name = "The Business Of Fashion"), Source(name = "CNN"),
	Source(name = "The New York Times"), Source(name = "Al jazeera"),
	Source(name = "Consumer Reports"), Source(name = "Channels"),
	Source(name = "Financial Times"), Source(name = "Fox News"),
	Source(name = "The Atlantic"), Source(name = "NTA"),
	Source(name = "Bloomberg"), Source(name = "Noahpinion")
)