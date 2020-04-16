package com.basecode.app.commonClass.abstractClass

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    protected var activity : Activity? =null

    protected var fragmentManagers: FragmentManager?=null
    protected var  bottomSheetDialogFragment:BottomSheetDialogFragment ? =null

    @Inject
    lateinit var sharedHelper: SharedHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var commonFunction: CommonFunction

    lateinit var eventBus: EventBus



    protected var disposable: CompositeDisposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        fragmentManagers = fragmentManager
        AndroidSupportInjection.inject(this)

        activity = getActivity()
        eventBus = EventBus.getDefault()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomSheet = (view!!.parent as View)
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)



    }

    override fun onDestroy() {
        super.onDestroy()
        if(bottomSheetDialogFragment!=null && bottomSheetDialogFragment!!.isAdded){
            bottomSheetDialogFragment!!.dismiss()
        }
    }
}