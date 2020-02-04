package com.heyzeusv.rickmortyverse.di.component

import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageViewModel
import com.heyzeusv.rickmortyverse.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 *  Component providing inject methods for ViewModels.
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelComponent {

    /**
     *  @param charPageVM CharacterPageViewModel in which to inject dependencies to
     */
    fun inject(charPageVM : CharacterPageViewModel)

    @Component.Builder
    interface Builder {

        fun build() : ViewModelComponent

        fun networkModule(networkModule : NetworkModule) : Builder
    }
}