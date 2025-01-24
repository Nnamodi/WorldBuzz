package com.roland.android.data_local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roland.android.data_local.entity.SavedArticleEntity

@Dao
interface SavedNewsDao {

	@Query("SELECT * FROM SavedArticleEntity")
	fun fetchSavedArticles(): List<SavedArticleEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveArticle(article: SavedArticleEntity)

	@Delete
	suspend fun unsaveArticle(article: SavedArticleEntity)

}