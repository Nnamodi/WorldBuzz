package com.roland.android.data_remote

import com.roland.android.data_remote.network.model.ArticleModel
import com.roland.android.data_remote.network.model.SourceDetailModel
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail

val expectedNewsData = listOf(
	Article(),
	Article(),
	Article(),
	Article(),
	Article()
)

val remoteNewsData = listOf(
	ArticleModel(),
	ArticleModel(),
	ArticleModel(),
	ArticleModel(),
	ArticleModel()
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