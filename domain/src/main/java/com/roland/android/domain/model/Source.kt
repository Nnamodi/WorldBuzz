package com.roland.android.domain.model

import com.roland.android.domain.util.Constant.FAVICON_URL

data class Source(
	val id: String = "",
	val name: String = ""
)

data class SourceDetail(
	val id: String = "",
	val name: String = "",
	val description: String = "",
	val url: String = "",
	val category: String = "",
	val language: String = "",
	val country: String = ""
) {
	fun iconUrl() = FAVICON_URL + url
}
