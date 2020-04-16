package com.localfolks.app.commonClass

import android.app.Activity
import com.basecode.app.commonClass.Interface.CommonInterface
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import timber.log.Timber
import java.util.*

class PlaceApiConnection(activity: Activity?, commonCallback: CommonInterface) {
    private val placesClient: PlacesClient
    private val commonCallback: CommonInterface
    fun getPlaceToLatLng(place_id: String?) { // Specify the fields to return.
        val placeFields = Arrays.asList(
            Place.Field.ADDRESS,
            Place.Field.ID,
            Place.Field.LAT_LNG
        )
        // Construct a request object, passing the place ID and fields array.
        val request = FetchPlaceRequest.builder(place_id!!, placeFields)
            .build()
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                commonCallback.Callback(place)
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) { // Handle error with given status code.
                    Timber.e("Place not found: %s", exception.message)
                }
            }
    }

    init {
        placesClient = Places.createClient(activity!!)
        this.commonCallback = commonCallback
    }
}