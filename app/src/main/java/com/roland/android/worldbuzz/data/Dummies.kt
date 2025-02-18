package com.roland.android.worldbuzz.data

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.worldbuzz.utils.Converters.refactor

val sampleNewsData = listOf(
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	),
	Article(
		source = Source(name = "CNN"),
		title = "What training do Volley Ball players need?",
		author = "Jones",
		publishedAt = "Feb 23, 2025"
	)
)

val sampleNewsPagingData = PagingData.from(sampleNewsData).refactor()