package com.roland.android.data_local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roland.android.domain.model.Source

@Entity
data class ArticleEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,
	val source: Source = Source(),
	val author: String = "",
	val title: String = "",
	val description: String = "",
	val url: String = "",
	val imageUrl: String = "",
	val publishedAt: String = "",
	val content: String = "",
	val categories: String = ""
)
