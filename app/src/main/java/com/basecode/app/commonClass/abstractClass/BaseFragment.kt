package com.basecode.app.commonClass.abstractClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    protected var activity : Activity ? =null

    protected var fragmentManagers: FragmentManager ?=null

    protected  var bottomSheetDialogFragment:BottomSheetDialogFragment ? =null

    lateinit var eventBus: EventBus


    @Inject
    lateinit var sharedHelper: SharedHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var commonFunction: CommonFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManagers = fragmentManager
        retainInstance = true
        eventBus = EventBus.getDefault()
        activity = getActivity()

    }

    protected fun moveTOFragment(fragment: Fragment,ids : Int){
        val fragmentTransaction = fragmentManagers?.beginTransaction()
        fragmentTransaction?.replace(ids, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commitAllowingStateLoss()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(bottomSheetDialogFragment!=null && bottomSheetDialogFragment!!.isAdded){
            bottomSheetDialogFragment!!.dismiss()
        }
    }
    protected fun setintent(intend : Intent, value:Int){
        startActivity(intend)
        when(value){
            1->{

            }
            2->{
                activity!!.finish()
            }
            3->{
                activity!!.finishAffinity()
            }
        }

    }
}