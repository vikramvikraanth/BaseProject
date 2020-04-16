package com.basecode.app.commonClass

import android.app.Activity
import android.app.Application
import android.widget.Toast
import com.google.gson.Gson
import com.kaopiz.kprogresshud.KProgressHUD
import timber.log.Timber
import javax.inject.Inject


class CommonFunction @Inject constructor(var application: Application) {

    protected var loader : KProgressHUD? =null



    fun showLoader(activity: Activity){
        try {
            if(loader!=null){
                return
            }

            loader  = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
            loader!!.show()
        } catch (e: Exception) {
            Timber.e(e)
        }

    }

    fun dismissLoader(){
        try {
            loader!!.dismiss()
            loader =null
        } catch (e: Exception) {
        }
    }

    fun showToast(value : String){
        Toast.makeText(application,value,Toast.LENGTH_LONG).show()
    }

    fun modelToGson(cObjection: Any): String? {
        val gson = Gson()
        val Json = gson.toJson(cObjection)  //see firstly above above
        return Json
    }

    fun gsonToModel(value: String, cObjection: Class<*>): Any? {
        val gson = Gson()
        return gson.fromJson(value, cObjection)

    }


}