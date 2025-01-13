package com.roland.android.domain

import androidx.paging.PagingData
import com.roland.android.domain.model.Article

val sampleNewsData = PagingData.from(
	listOf(
		Article(),
		Article(),
		Article(),
		Article(),
		Article()
	)
)