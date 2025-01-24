package com.roland.android.data_local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roland.android.data_local.entity.ArticleEntity
import com.roland.android.data_local.entity.ReadArticleEntity
import com.roland.android.data_local.entity.SavedArticleEntity
import com.roland.android.data_local.entity.SourceDetailEntity
import com.roland.android.data_local.utils.TypeConverter

@Database(
	entities = [
		ArticleEntity::class,
		ReadArticleEntity::class,
		SavedArticleEntity::class,
		SourceDetailEntity::class
	],
	version = 1
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun newsDao(): NewsDao

	abstract fun historyDao(): HistoryDao

	abstract fun savedNewsDao(): SavedNewsDao

	abstract fun sourceDao(): SourceDao

}