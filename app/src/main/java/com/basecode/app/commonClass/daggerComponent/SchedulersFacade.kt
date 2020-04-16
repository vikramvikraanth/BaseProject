package com.basecode.app.commonClass.daggerComponent

import dagger.Module
import dagger.Provides
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Module
class SchedulersFacade @Inject constructor() {

    /**
     * IO thread pool scheduler
     */
    @Provides
    fun io(): Scheduler {
        return Schedulers.io()
    }

    /**
     * Computation thread pool scheduler
     */
    @Provides
    fun computation(): Scheduler {
        return Schedulers.computation()
    }

    /**
     * Main Thread scheduler
     */
    @Provides
    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun <T> applyAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(io())
                .observeOn(ui())
        }
    }
}