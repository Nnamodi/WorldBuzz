package com.roland.android.data_remote.network.model

import com.squareup.moshi.Json

data class SourceDetailModel(
	@Json(name = "id")
	val id: String = "",
	@Json(name = "name")
	val name: String = "",
	@Json(name = "description")
	val description: String = "",
	@Json(name = "url")
	val url: String = "",
	@Json(name = "category")
	val category: String = "",
	@Json(name = "language")
	val language: String = "",
	@Json(name = "country")
	val country: String = ""
)