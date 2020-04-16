package com.carwash.provider.utility.ImagePicker.Interface

interface FileResultCallback<T> {
	fun onResultCallback(files: List<T>)
}