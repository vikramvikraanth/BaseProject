package com.basecode.app.commonClass

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AsyncGeocoder(
    context: Context,
    private val disposable: CompositeDisposable,
    private val callback: Callback
) {
    private val geocoder: Geocoder

    init {
        geocoder = Geocoder(context)

    }

    fun getReverseGeoCoding(location: LatLng) {
        try {
            disposable.add(
                Observable.just(geocoder.getFromLocation(location.latitude, location.longitude, 1))
                    .subscribeOn(Schedulers.io())
                    .doOnError { Timber.e(it) }
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { Address -> callback.success(Address[0]) },
                        { e -> callback.failure(e) })
            )
        } catch (e: Exception) {
            Timber.e(e)
            callback.failure(e)

        }

    }

    interface Callback {
        fun success(address: Address)

        fun failure(e: Throwable)
    }
}