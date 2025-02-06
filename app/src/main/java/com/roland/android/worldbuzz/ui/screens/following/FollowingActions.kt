package com.roland.android.worldbuzz.ui.screens.following

import com.roland.android.domain.model.CategoryModel
import com.roland.android.domain.model.SourceDetail

sealed class FollowingActions {

	data class SubscribedToCategory(
		val category: CategoryModel,
		val subscribe: Boolean
	): FollowingActions()

	data class SubscribedToSource(
		val source: SourceDetail,
		val subscribe: Boolean
	): FollowingActions()

}