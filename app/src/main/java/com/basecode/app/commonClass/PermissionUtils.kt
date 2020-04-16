package com.basecode.app.commonClass

import android.Manifest
import android.app.Activity
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

object PermissionUtils {
    val LocationPermission =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val STORAGEPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun getRunTimePermission(
        activity: Activity,
        permissionArray: Array<String>,
        OnPermissionListener: OnPermissionListener,
        message: String, tittle: String, compositeDisposable : CompositeDisposable
    ) {
        compositeDisposable.add(
            TedRx2Permission.with(activity)
                .setRationaleTitle(tittle)
                .setRationaleMessage(message)
                .setPermissions(*permissionArray)
                .request()
                .subscribe({ tedPermissionResult ->
                    if (tedPermissionResult.isGranted)
                        OnPermissionListener.requestedPermissionSuccess()
                    else
                        OnPermissionListener.requestedPermissionPermanentlyDented()
                }, { throwable ->
                    Timber.e(throwable)
                    OnPermissionListener.requestedPermissionPermanentlyDented()
                })
        )
    }
    interface OnPermissionListener {
        fun requestedPermissionSuccess()
        fun requestedPermissionPermanentlyDented()
    }
}