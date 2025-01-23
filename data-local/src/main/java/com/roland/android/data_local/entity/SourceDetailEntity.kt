package com.roland.android.data_local.entity

import androidx.room.Entity

@Entity
data class SourceDetailEntity(
	val id: String = "",
	val name: String = "",
	val description: String = "",
	val url: String = "",
	val category: String = "",
	val language: String = "",
	val country: String = "",
	val subscribed: Boolean = false
)
