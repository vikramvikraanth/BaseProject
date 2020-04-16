package com.basecode.app.commonClass.abstractClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity() {

    protected var activity : Activity = this
    protected var context : Context = this
    protected lateinit var fragmentManager: FragmentManager
    protected var disposable: CompositeDisposable ?=null

    @Inject
    lateinit var sharedHelper: SharedHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var commonFunction: CommonFunction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager = supportFragmentManager
        disposable = CompositeDisposable()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onDestroy() {
        super.onDestroy()
        if(disposable!!.isDisposed){
            disposable?.clear()
        }
    }

  protected fun setintent(intend :Intent,value:Int){
      startActivity(intend)
      when(value){
          1->{
              finish()
          }
          2->{
              finishAffinity()
          }
      }

    }

    protected fun moveTOFragment(fragment: Fragment,ids : Int){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(ids, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()

    }
    fun isGPS(): Boolean? {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}