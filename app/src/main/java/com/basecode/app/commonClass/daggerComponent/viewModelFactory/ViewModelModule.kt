package com.basecode.app.commonClass.daggerComponent.viewModelFactory

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


@Module
internal abstract class ViewModelModule {


  /*  @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(detailsViewModel: HomeViewModel): ViewModel*/



    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
