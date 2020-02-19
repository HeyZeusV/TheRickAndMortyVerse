package com.heyzeusv.rickmortyverse.di.component

import com.heyzeusv.rickmortyverse.di.module.NetworkModule
import com.heyzeusv.rickmortyverse.viewmodels.*
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

    /**
     *  @param charDetailVM CharacterDetailViewModel in which to inject dependencies to
     */
    fun inject(charDetailVM : CharacterDetailViewModel)

    /**
     *  @param episPageVM EpisodePageViewModel in which to inject dependencies to
     */
    fun inject(episPageVM : EpisodePageViewModel)

    /**
     *  @param episDetailVM EpisodeDetailViewModel in which to inject dependencies to
     */
    fun inject(episDetailVM : EpisodeDetailViewModel)

    /**
     *  @param locPageVM LocationPageViewModel in which to inject dependencies to
     */
    fun inject(locPageVM : LocationPageViewModel)

    /**
     *  @param locDetailVM LocationDetailViewModel in which to inject dependencies to
     */
    fun inject(locDetailVM : LocationDetailViewModel)

    @Component.Builder
    interface Builder {

        fun build() : ViewModelComponent

        fun networkModule(networkModule : NetworkModule) : Builder
    }
}