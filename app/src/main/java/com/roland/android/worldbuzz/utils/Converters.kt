package com.roland.android.worldbuzz.utils

import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail
import kotlinx.coroutines.flow.MutableStateFlow

object Converters {
	fun Article.toJson(): String {
		val gson = Gson()
		return gson.toJson(this)
	}

	fun String.toArticle(): Article {
		val gson = Gson()
		val type = object : TypeToken<Article>() {}.type
		return gson.fromJson(this, type)
	}

	fun SourceDetail.update(subscribed: Boolean) = SourceDetail(
		id = id,
		name = name,
		description = description,
		url = url,
		category = category,
		language = language,
		country = country,
		subscribed = subscribed
	)

	fun PagingData<Article>.refactor(): MutableStateFlow<PagingData<Article>> {
		return MutableStateFlow(this)
	}

	fun String.capitalizeFirstLetter(): String {
		return substring(0, 1).uppercase() + substring(1)
	}
}