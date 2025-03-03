package com.roland.android.data_local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.roland.android.data_local.utils.Converters.SEPARATOR
import com.roland.android.domain.model.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val SUBSCRIBED_CATEGORY_KEY = stringPreferencesKey("subscribed_category")

class SubscribedCategoryStore(
	private val datastore: DataStore<Preferences>
) {
	fun getSubscribedCategories(): Flow<List<CategoryModel>> {
		return datastore.data.map { pref ->
			val categoryName = pref[SUBSCRIBED_CATEGORY_KEY] ?: CategoryModel.All.name
			categoryName.split(SEPARATOR).map { name ->
				CategoryModel.valueOf(name)
			}
		}
	}

	suspend fun updateSubscribedCategories(categoryModels: List<CategoryModel>) {
		datastore.edit { pref ->
			val categories = categoryModels
				.map { it.category }
				.joinToString { SEPARATOR }
			pref[SUBSCRIBED_CATEGORY_KEY] = categories
		}
	}
}