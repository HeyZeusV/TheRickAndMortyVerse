package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heyzeusv.rickmortyverse.di.component.DaggerViewModelComponent
import com.heyzeusv.rickmortyverse.di.component.ViewModelComponent
import com.heyzeusv.rickmortyverse.di.module.NetworkModule
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *  Base class for ViewModels. Includes DI, error handling,
 *  and functions to display/hide ProgressBar.
 */
abstract class BaseViewModel : ViewModel() {

    private val injector : ViewModelComponent = DaggerViewModelComponent
        .builder()
        .networkModule(NetworkModule())
        .build()

    // used to make REST calls
    protected lateinit var subscription : Disposable

    // visibility of loading ProgressBar while call is being made
    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    // visibility of error TextView/Button
    private val _errorVisibility : MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    val errorVisibility : LiveData<Int>
        get() = _errorVisibility

    // error message to be displayed
    private val _errorMessage : MutableLiveData<Int> = MutableLiveData(0)
    val errorMessage : LiveData<Int>
        get() = _errorMessage

    // required onClick for error Button used by almost all fragments
    abstract val errorOnClick : View.OnClickListener

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

    /**
     *  Runs when call is first made. Displays ProgressBar and hides error TextView/Button in case
     *  they were being displayed.
     */
    protected fun onStart() {

        _loadingVisibility.value = View.VISIBLE
        _errorVisibility  .value = View.INVISIBLE
    }

    /**
     *  Runs when call is finished, doesn't matter if error or success. Hides ProgressBar.
     */
    protected fun onFinish() { _loadingVisibility.value = View.INVISIBLE }

    /**
     *  Runs if error occurs during call. Selects error message depending on Exception thrown
     *  and displays TextView/Button.
     *
     *  @param error error that is thrown
     */
    protected fun onError(error : Throwable) {

        when (error) {

            is UnknownHostException   -> _errorMessage.value = 0
            is SocketTimeoutException -> _errorMessage.value = 1
            else                      -> _errorMessage.value = 2
        }
        _errorVisibility.value = View.VISIBLE
    }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        inject()
    }
}