package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.LocationPage
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationPageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var subscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _locPage : MutableLiveData<LocationPage> = MutableLiveData()
    val locPage : LiveData<LocationPage>
        get() = _locPage

    @Suppress("UnstableApiUsage")
    private fun loadLocations() {

        subscription =  apiService.getLocationPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLocPageStart()  }
            .doOnTerminate { onRetrieveLocPageFinish() }
            .subscribe(
                { result : LocationPage -> onRetrieveLocPageSuccess(result) },
                { onRetrieveLocPageError() }
            )
    }

    private fun onRetrieveLocPageStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveLocPageFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveLocPageSuccess(locPage : LocationPage) { _locPage.value = locPage }

    private fun onRetrieveLocPageError() { }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        loadLocations()
    }
}