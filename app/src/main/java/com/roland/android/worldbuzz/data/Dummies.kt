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
	SourceDetail(name = "a"), SourceDetail(name = "k"),
	SourceDetail(name = "b"), SourceDetail(name = "l"),
	SourceDetail(name = "c"), SourceDetail(name = "m"),
	SourceDetail(name = "d"), SourceDetail(name = "n"),
	SourceDetail(name = "e"), SourceDetail(name = "o"),
	SourceDetail(name = "f"), SourceDetail(name = "p")
)