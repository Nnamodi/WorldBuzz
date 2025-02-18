package com.roland.android.worldbuzz.utils

import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.lerp
import kotlin.math.absoluteValue

fun Modifier.animatePagerItem(
	itemPage: Int,
	pagerState: PagerState
): Modifier {
	return graphicsLayer {
		val pageOffset = (
			(pagerState.currentPage - itemPage) + pagerState.currentPageOffsetFraction
		).absoluteValue.coerceIn(0f, 1f)
		scaleX = 1f - (pageOffset * 0.1f)
		scaleY = 1f - (pageOffset * 0.1f)
		alpha = lerp(
			start = Dp(0.5f),
			stop = Dp(0.5f),
			fraction = 1f - pageOffset
		).value
	}
}