package com.roland.android.data_local

import com.roland.android.data_local.entity.ArticleEntity
import com.roland.android.data_local.entity.ReadArticleEntity
import com.roland.android.data_local.entity.SavedArticleEntity
import com.roland.android.data_local.entity.SourceDetailEntity
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail

val expectedNewsData = listOf(
	Article(),
	Article(),
	Article(),
	Article(),
	Article()
)

val localTrendingNewsData = listOf(
	ArticleEntity(categories = "|"),
	ArticleEntity(categories = "|"),
	ArticleEntity(categories = "|"),
	ArticleEntity(categories = "|"),
	ArticleEntity(categories = "|")
)

val localRecommendedNewsData = listOf(
	ArticleEntity(),
	ArticleEntity(),
	ArticleEntity(),
	ArticleEntity(),
	ArticleEntity()
)

val localReadNewsData = listOf(
	ReadArticleEntity(),
	ReadArticleEntity(),
	ReadArticleEntity(),
	ReadArticleEntity(),
	ReadArticleEntity()
)

val localSavedNewsData = listOf(
	SavedArticleEntity(),
	SavedArticleEntity(),
	SavedArticleEntity(),
	SavedArticleEntity(),
	SavedArticleEntity()
)

val expectedNewsSource = SourceDetail("nri")

val expectedNewsSources = listOf(
	expectedNewsSource,
	expectedNewsSource,
	expectedNewsSource,
	expectedNewsSource,
	expectedNewsSource
)

val localNewsSource = SourceDetailEntity("nri")

val localNewsSources = listOf(
	localNewsSource,
	localNewsSource,
	localNewsSource,
	localNewsSource,
	localNewsSource
)

val newsSource = Source("nri")

val newsSources = listOf(
	newsSource,
	newsSource,
	newsSource,
	newsSource,
	newsSource
)