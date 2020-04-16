package com.basecode.app.commonClass.daggerComponent

import android.app.Application
import com.localfolks.app.commonClass.appController.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication


@Component(modules = arrayOf(
    ContextModule::class,  AndroidSupportInjectionModule::class,
    SchedulersFacade::class, RetrofitGenerator::class ,
    ActivityBindingModule::class))
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: AppController)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
