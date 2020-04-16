package com.localfolks.app.commonClass.abstractClass

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.basecode.app.networkApi.Response
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    protected val disable: CompositeDisposable = CompositeDisposable()
    protected val response = MutableLiveData<Response>()



   /* protected fun requestBody(value: String): RequestBody {

        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }



    protected fun createMultiPartImage(value: String, key: String): MultipartBody.Part? {
        var multipartBody: MultipartBody.Part? = null
        *//*disable.add(
            Compressor(application)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(100)
                .compressToFileAsFlowable(File(value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                })
        )*//*
        if (value.isEmpty()) {
            return multipartBody
        }
        if (value.startsWith("http") || value.startsWith("https")) {
            multipartBody = MultipartBody.Part.createFormData(
                key,
                "",
                RequestBody.create(getMimeType(File(value).toString())!!.toMediaTypeOrNull(), "")
            )
        } else {
            multipartBody = MultipartBody.Part.createFormData(
                key,
                File(value).getName(),
                RequestBody.create(
                    getMimeType(File(value).toString())!!.toMediaTypeOrNull(),
                    File(value)
                )
            )

        }
        return multipartBody

    }*/

    protected @Inject
    lateinit var application: Application



    fun getMimeType(url: String): String? {
        var type: String? = ""
        val extension = MimeTypeMap.getFileExtensionFromUrl(url.replace(" ", "%20"))
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        if (type == null) {
            return "jpg"
        }
        return type
    }

    override fun onCleared() {
        super.onCleared()
        try {
            disable.clear()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    protected fun onError(throwable: Throwable) {
        Toast.makeText(application, getErrorBody(throwable), Toast.LENGTH_LONG).show()
    }

    protected fun switchActivity(Objection: Class<*>, isFrom: Int, activity: Activity) {
        activity.startActivity(Intent(application, Objection))
        when (isFrom) {
            1 -> {
                // TODO just no need to finish
            }
            2 ->
                // TODO just finishing the single activity
                activity.finish()
            3 ->
                // TODO  finishing All previous activity
                activity.finishAffinity();
        } // TODO Not finishing

    }

    fun getErrorBody(throwable: Throwable): String {
        try {
            when (throwable) {
                is HttpException -> {
                    assert(throwable.response()!!.errorBody() != null)
                    val errorBody = throwable.response()!!.errorBody()!!.string()
                    Timber.e("errorBody: %s", errorBody)
                    val jsonObject = JSONObject(errorBody)
                    return if (jsonObject.has("message")) {
                        jsonObject.optString("message")
                    }else if (jsonObject.has("error")) {
                        jsonObject.optString("error")
                    } else {
                        "Something went wrong!!"
                    }
                }
                is UnknownHostException -> {
                    return "No internet connection"
                }
                else -> return if (throwable.message == null) {
                    "Something went Wrong!"
                } else {
                    throwable.message.toString()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return "Something went wrong"
        } catch (e: IOException) {
            e.printStackTrace()
            return "Something went wrong"
        }
    }

}