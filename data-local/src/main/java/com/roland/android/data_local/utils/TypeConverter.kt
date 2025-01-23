package com.roland.android.data_local.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roland.android.domain.model.Source

class TypeConverter {
	@TypeConverter
	fun fromSource(source: Source): String {
		val gson = Gson()
		return gson.toJson(source)
	}

	@TypeConverter
	fun toSource(sourceJson: String): Source {
		val gson = Gson()
		val type = object : TypeToken<Source>() {}.type
		return gson.fromJson(sourceJson, type)
	}
}