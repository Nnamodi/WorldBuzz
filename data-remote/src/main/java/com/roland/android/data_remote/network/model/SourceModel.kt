package com.roland.android.data_remote.network.model

import com.squareup.moshi.Json

data class SourceModel(
	@Json(name = "id")
	val id: String? = null,
	@Json(name = "name")
	val name: String = ""
)
