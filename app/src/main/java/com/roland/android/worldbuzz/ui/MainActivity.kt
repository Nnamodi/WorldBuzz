package com.roland.android.worldbuzz.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.roland.android.worldbuzz.R
import com.roland.android.worldbuzz.ui.components.appbars.NavBar
import com.roland.android.worldbuzz.ui.navigation.AppRoute
import com.roland.android.worldbuzz.ui.theme.WorldBuzzTheme

class MainActivity : ComponentActivity() {
	@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(R.style.Theme_WorldBuzz)
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			WorldBuzzTheme {
				val navController = rememberNavController()

				Scaffold(
					bottomBar = { NavBar(navController) }
				) { _ ->
					AppRoute(navController)
				}
			}
		}
	}
}