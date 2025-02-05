package com.roland.android.worldbuzz.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roland.android.domain.model.Article
import com.roland.android.domain.model.SourceDetail

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
}