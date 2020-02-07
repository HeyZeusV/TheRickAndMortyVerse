package com.heyzeusv.rickmortyverse.viewmodels

import androidx.lifecycle.ViewModel
import com.heyzeusv.rickmortyverse.di.component.DaggerViewModelComponent
import com.heyzeusv.rickmortyverse.di.component.ViewModelComponent
import com.heyzeusv.rickmortyverse.di.module.NetworkModule

/**
 *  Base class for ViewModels that will required dependency injections.
 */
abstract class InjectViewModel : ViewModel() {

    private val injector : ViewModelComponent = DaggerViewModelComponent
        .builder()
        .networkModule(NetworkModule())
        .build()

    /**
     *  Injects the required dependencies.
     */
    private fun inject() {

        when (this) {

            is CharacterPageViewModel   -> injector.inject(this)
            is CharacterDetailViewModel -> injector.inject(this)
        }
    }

    init {

        inject()
    }
}