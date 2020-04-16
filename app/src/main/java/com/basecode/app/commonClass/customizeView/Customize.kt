package com.basecode.app.commonClass.customizeView

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.basecode.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Customize {
    private val gridSpacing = dp2px(2)
    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    @BindingAdapter("url_circle")
    @JvmStatic
    fun glideAppCircle(Images: ImageView, imagePath: Any?) {
        GlideApp.with(Images.context)
            .load(imagePath)
            .circleCrop()
            .into(Images)

    }

    @BindingAdapter("url")
    @JvmStatic
    fun GlideApps(Images: ImageView, imagePath: Any?) {
        if (imagePath == null) {
            GlideApp.with(Images.context)
                .load(imagePath
                )
                .into(Images)

        }

    }
    @BindingAdapter("main", "leght")
    @JvmStatic
    fun setBoldString(view: TextView, maintext: String, lenght: Int) {
        val spannable = SpannableString(maintext)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.places_text_black_alpha_26)),
            0, lenght,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.text = spannable
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("data")
    @JvmStatic
    fun setTimeConversion(view: TextView, data: String) {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd/MM/yyyy")

        var d: Date? = null
        try {
            d = input.parse(data)
            val date = output.format(d)
            view.setText(date)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    @BindingAdapter("onNavigationItemSelected")
    @JvmStatic
    fun setOnNavigationItemSelectedListener(
        view: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener?
    ) {
        view.setOnNavigationItemSelectedListener(listener)
    }

    @BindingAdapter("load_allLead")
    @JvmStatic
    fun loadUsers(recyclerView: RecyclerView, adapter: Any?) {
        when (adapter) {
          /*  is ExploreAdapter -> {
                recyclerView.adapter = adapter as RecyclerView.Adapter<*>?

            }*/


        }
    }
    fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        val byteFormat: ByteArray = stream.toByteArray()
        // get the base 64 string
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
    }
}