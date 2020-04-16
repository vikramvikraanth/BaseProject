package com.localfolks.app.commonClass.appController

import android.os.StrictMode
import android.util.Log
import androidx.multidex.MultiDex
import com.basecode.app.commonClass.daggerComponent.ApplicationComponent
import com.google.android.libraries.places.api.Places
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import timber.log.Timber


class AppController : DaggerApplication() {

    companion object{
        var token : String ? =""
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: ApplicationComponent = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }


    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        Places.initialize(this, BuildConfig.googleApiKey)

        MultiDex.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        } else {
            Timber.plant( CrashReportingTree());
        }

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(resources.getString(R.string.app_front))
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            if (t != null) {
                if (priority == Log.ERROR) {
                 //   FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                 //   FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }

}