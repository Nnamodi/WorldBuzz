package com.roland.android.worldbuzz.ui.components.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.Long.Companion.MAX_VALUE

@Composable
fun Snackbar(
	message: String,
	modifier: Modifier = Modifier,
	paddingValues: PaddingValues,
	actionLabel: String? = null,
	action: () -> Unit = {},
	duration: SnackbarDuration = SnackbarDuration.Short,
	onDismiss: () -> Unit = {}
) {
	val snackbarMessage = rememberSaveable(message) { mutableStateOf<String?>(message) }

	AnimatedVisibility(
		visible = snackbarMessage.value != null,
		enter = slideInVertically(tween(200)) { it },
		exit = slideOutVertically(tween(100)) { it },
	) {
		SnackbarVisuals(
			message = message,
			modifier = modifier.padding(bottom = paddingValues.calculateBottomPadding()),
			actionLabel = actionLabel,
			action = {
				snackbarMessage.value = null
				action()
			}
		)
	}

	LaunchedEffect(message) {
		delay(duration.millis)
		snackbarMessage.value = null
		onDismiss()
	}

	DisposableEffect(Unit) {
		onDispose {
			if (duration == SnackbarDuration.Indefinite) return@onDispose
			snackbarMessage.value = null
			onDismiss()
		}
	}
}

@Composable
private fun SnackbarVisuals(
	message: String,
	modifier: Modifier,
	actionLabel: String?,
	action: () -> Unit
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
			.clip(MaterialTheme.shapes.medium)
			.background(colorScheme.onBackground),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = message,
			modifier = Modifier
				.weight(1f)
				.padding(20.dp),
			color = colorScheme.background,
			style = MaterialTheme.typography.bodyMedium
		)
		Spacer(Modifier.weight(1f))
		actionLabel?.let { label ->
			TextButton(
				onClick = action,
				modifier = Modifier.padding(end = 10.dp),
				colors = textButtonColors(contentColor = colorScheme.inversePrimary)
			) {
				Text(label)
			}
		}
	}
}

enum class SnackbarDuration(val millis: kotlin.Long) {
	Short(4000L),
	Long(10000L),
	Indefinite(MAX_VALUE)
}