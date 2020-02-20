package com.heyzeusv.rickmortyverse.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heyzeusv.rickmortyverse.di.component.DaggerViewModelComponent
import com.heyzeusv.rickmortyverse.di.component.ViewModelComponent
import com.heyzeusv.rickmortyverse.di.module.NetworkModule
import io.reactivex.disposables.Disposable

/**
 *  Base class for ViewModels that will required dependency injections.
 */
abstract class InjectViewModel : ViewModel() {

    private val injector : ViewModelComponent = DaggerViewModelComponent
        .builder()
        .networkModule(NetworkModule())
        .build()

    protected lateinit var subscription : Disposable

    protected val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    /**
     *  Injects the required dependencies.
     */
    private fun inject() {

        when (this) {

            is CharacterPageViewModel   -> injector.inject(this)
            is CharacterDetailViewModel -> injector.inject(this)
            is EpisodePageViewModel     -> injector.inject(this)
            is EpisodeDetailViewModel   -> injector.inject(this)
            is LocationPageViewModel    -> injector.inject(this)
            is LocationDetailViewModel  -> injector.inject(this)
        }
    }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        inject()
    }
}