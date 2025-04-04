package com.roland.android.data_repository

import androidx.paging.PagingData
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.Source
import com.roland.android.domain.model.SourceDetail

val sampleNewsData = listOf(
	Article(),
	Article(),
	Article(),
	Article(),
	Article()
)

val sampleNewsPagingData = PagingData.from(sampleNewsData)

val sampleNewsSource = Source()

val sampleNewsSources = listOf(
	sampleNewsSource,
	sampleNewsSource,
	sampleNewsSource,
	sampleNewsSource,
	sampleNewsSource
)

val sampleNewsSourceDetail = SourceDetail()

val sampleNewsSourcesII = listOf(
	sampleNewsSourceDetail,
	sampleNewsSourceDetail,
	sampleNewsSourceDetail,
	sampleNewsSourceDetail,
	sampleNewsSourceDetail
)