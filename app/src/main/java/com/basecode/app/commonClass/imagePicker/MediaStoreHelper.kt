package com.basecode.app.commonClass.imagePicker

import android.content.ContentResolver
import android.os.Bundle
import com.basecode.app.commonClass.imagePicker.Files.PhotoDirectory
import com.carwash.provider.utility.ImagePicker.Interface.FileResultCallback

object MediaStoreHelper {
	
	fun getDirs(contentResolver: ContentResolver, args: Bundle, resultCallback: FileResultCallback<PhotoDirectory>) {
		PhotoScannerTask(contentResolver,args,resultCallback).execute()
	}
	
}