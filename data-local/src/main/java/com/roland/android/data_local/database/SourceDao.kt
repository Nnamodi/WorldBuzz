package com.roland.android.data_local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roland.android.data_local.entity.SourceDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

	@Query("SELECT * FROM SourceDetailEntity")
	fun fetchAllSources(): Flow<List<SourceDetailEntity>>

	@Query("SELECT * FROM SourceDetailEntity WHERE subscribed LIKE 1")
	fun fetchSubscribedSources(): Flow<List<SourceDetailEntity>>

	@Query("SELECT * FROM SourceDetailEntity WHERE id LIKE :sourceId")
	fun fetchSourceDetails(sourceId: String): Flow<SourceDetailEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveAllSources(sources: List<SourceDetailEntity>)

}