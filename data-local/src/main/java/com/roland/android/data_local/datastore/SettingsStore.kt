package com.roland.android.data_local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.roland.android.domain.model.LanguageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal val LANGUAGE_KEY = stringPreferencesKey("language")
internal val TEXT_SIZE_KEY = intPreferencesKey("text_size")
internal val ENABLE_HISTORY_KEY = booleanPreferencesKey("enable_history")

class SettingsStore(
	private val datastore: DataStore<Preferences>
) {
	fun getSelectedLanguage(): Flow<LanguageModel> {
		return datastore.data.map { pref ->
			val languageCode = pref[LANGUAGE_KEY] ?: ""
			LanguageModel.valueOf(languageCode)
		}
	}

	suspend fun selectLanguage(languageCode: String) {
		datastore.edit {
			it[LANGUAGE_KEY] = languageCode
		}
	}

	fun getSelectedTextSize(): Flow<Int> {
		return datastore.data.map { pref ->
			pref[TEXT_SIZE_KEY] ?: 14
		}
	}

	suspend fun selectTextSize(textSize: Int) {
		datastore.edit {
			it[TEXT_SIZE_KEY] = textSize
		}
	}

	fun checkIsReadingHistoryEnable(): Flow<Boolean> {
		return datastore.data.map { pref ->
			pref[ENABLE_HISTORY_KEY] ?: true
		}
	}

	suspend fun enableReadingHistory(enable: Boolean) {
		datastore.edit {
			it[ENABLE_HISTORY_KEY] = enable
		}
	}
}