package com.roland.android.data_local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.roland.android.data_local.entity.ReadArticleEntity

@Dao
interface HistoryDao {

	@Query("SELECT * FROM ReadArticleEntity")
	fun fetchReadingHistory(): List<ReadArticleEntity>

	@Insert
	suspend fun addArticleToHistory(article: ReadArticleEntity)

	@Delete
	suspend fun removeFromReadingHistory(article: ReadArticleEntity)

	@Query("DELETE FROM ReadArticleEntity")
	suspend fun clearReadingHistory()

}