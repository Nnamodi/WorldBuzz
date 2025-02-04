package com.roland.android.worldbuzz.data

sealed class State<T: Any> {

	data class Success<T: Any>(val data: T) : State<T>()

	data class Error<T: Any>(val errorMessage: String) : State<T>()

}