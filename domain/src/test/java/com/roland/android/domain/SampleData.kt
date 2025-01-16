package com.roland.android.domain

import androidx.paging.PagingData
import com.roland.android.domain.model.Article

val sampleNewsData = listOf(
	Article(),
	Article(),
	Article(),
	Article(),
	Article()
)

val sampleNewsPagingData = PagingData.from(sampleNewsData)