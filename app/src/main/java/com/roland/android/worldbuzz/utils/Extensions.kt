package com.roland.android.worldbuzz.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.ui.graphics.vector.ImageVector
import com.roland.android.domain.model.CategoryModel

object Extensions {
	fun CategoryModel.icon(): ImageVector {
		return when (this) {
			CategoryModel.All -> Icons.Outlined.TravelExplore
			CategoryModel.Business -> Icons.Outlined.Business
			CategoryModel.Entertainment -> Icons.Outlined.LocalMovies
			CategoryModel.Health -> Icons.Outlined.HealthAndSafety
			CategoryModel.Science -> Icons.Outlined.Science
			CategoryModel.Sports -> Icons.Outlined.SportsVolleyball
			CategoryModel.Technology -> Icons.Outlined.Biotech
		}
	}
}