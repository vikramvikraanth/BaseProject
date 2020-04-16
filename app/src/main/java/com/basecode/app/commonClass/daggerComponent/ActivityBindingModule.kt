package com.basecode.app.commonClass.daggerComponent

import com.basecode.app.MainActivity
import com.basecode.app.commonClass.daggerComponent.activityBinding.WelcomeFragments
import dagger.android.ContributesAndroidInjector
import dagger.Module


@Module
abstract class ActivityBindingModule {


    @ContributesAndroidInjector(modules = [WelcomeFragments::class])
    internal abstract fun bindSplashActivity(): MainActivity

}
