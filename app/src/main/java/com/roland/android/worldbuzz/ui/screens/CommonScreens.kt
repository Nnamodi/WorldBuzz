package com.roland.android.worldbuzz.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roland.android.worldbuzz.data.State

@Composable
fun <T: Any>CommonScreen(
	state: State<T>?,
	paddingValues: PaddingValues = PaddingValues(0.dp),
	loadingScreen: @Composable (String?) -> Unit,
	successScreen: @Composable (T) -> Unit
) {
	Box(Modifier.padding(paddingValues)) {
		when (state) {
			null -> loadingScreen(null)
			is State.Error -> loadingScreen(state.errorMessage)
			is State.Success -> successScreen(state.data)
		}
	}
}

@Composable
fun <T: Any, T2: Any>CommonScreen(
	state: State<T>?,
	state2: State<T2>?,
	paddingValues: PaddingValues = PaddingValues(0.dp),
	loadingScreen: @Composable (String?) -> Unit,
	successScreen: @Composable (T, T2) -> Unit
) {
	Box(Modifier.padding(paddingValues)) {
		when {
			(state == null) || (state2 == null) -> loadingScreen(null)
			state is State.Error -> loadingScreen(state.errorMessage)
			state2 is State.Error -> loadingScreen(state2.errorMessage)
			(state is State.Success) && (state2 is State.Success) -> successScreen(state.data, state2.data)
		}
	}
}

@Composable
fun CommonScaffold(
	modifier: Modifier = Modifier,
	topBar: @Composable () -> Unit = {},
	containerColor: Color = MaterialTheme.colorScheme.background,
	contentColor: Color = contentColorFor(containerColor),
	content: @Composable (PaddingValues) -> Unit
) {
	Scaffold(
		modifier = modifier,
		topBar = topBar,
		bottomBar = {},
		snackbarHost = {},
		floatingActionButton = {},
		floatingActionButtonPosition = FabPosition.End,
		containerColor = containerColor,
		contentColor = contentColor,
		contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
		content = {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.BottomStart
			) {
				content(it)
			}
		}
	)
}