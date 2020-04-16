package com.localfolks.app.commonClass

object Constants {
    // location updates interval - 10sec
    val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000

    val REQUEST_CHECK_SETTINGS = 100

    val GOOGLEAPIKEY = "AIzaSyC0pCBIt70eysOQ6tLXz8tbTn1NLgO6y84"

    //Map Zooming Size
    val MAP_ZOOM_SIZE = 14f

    fun capitalizeFirstLetter(original: String?): String? {
        return if (original == null || original.length == 0) {
            original
        } else original.substring(0, 1).toUpperCase() + original.substring(1)
    }

    val REQUEST_IMAGE_CAPTURE = 1


    var strExpiryDate:String ?=""

    var strFileDocument:String ?=""
}
