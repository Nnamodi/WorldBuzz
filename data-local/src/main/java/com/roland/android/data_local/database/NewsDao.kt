package com.roland.android.data_local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roland.android.data_local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

	@Query("SELECT * FROM ArticleEntity WHERE categories LIKE :categories")
	fun fetchTrendingNews(categories: String): Flow<List<ArticleEntity>>

	@Query("SELECT * FROM ArticleEntity WHERE categories LIKE :category")
	fun fetchRecommendedNews(category: String): Flow<List<ArticleEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveTrendingNews(news: List<ArticleEntity>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveRecommendedNews(news: List<ArticleEntity>)

	@Query("DELETE FROM ArticleEntity")
	fun clearCachedNews()

}