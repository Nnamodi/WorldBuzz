package com.roland.android.data_remote

import com.roland.android.data_remote.network.model.ArticleModel
import com.roland.android.data_remote.network.model.SourceDetailModel
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail

val expectedNewsData = listOf(
	Article(publishedAt = "February 6, 2025"),
	Article(publishedAt = "February 6, 2025"),
	Article(publishedAt = "February 6, 2025"),
	Article(publishedAt = "February 6, 2025"),
	Article(publishedAt = "February 6, 2025")
)

val remoteNewsData = listOf(
	ArticleModel(publishedAt = "2025-02-06T13:45:12Z"),
	ArticleModel(publishedAt = "2025-02-06T13:45:12Z"),
	ArticleModel(publishedAt = "2025-02-06T13:45:12Z"),
	ArticleModel(publishedAt = "2025-02-06T13:45:12Z"),
	ArticleModel(publishedAt = "2025-02-06T13:45:12Z")
)

val expectedNewsSources = listOf(
	SourceDetail(),
	SourceDetail(),
	SourceDetail(),
	SourceDetail(),
	SourceDetail()
)

val remoteNewsSources = listOf(
	SourceDetailModel(),
	SourceDetailModel(),
	SourceDetailModel(),
	SourceDetailModel(),
	SourceDetailModel()
)