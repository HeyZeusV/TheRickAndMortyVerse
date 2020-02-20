package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.LocationNameType
import com.heyzeusv.rickmortyverse.models.LocationPage
import com.heyzeusv.rickmortyverse.models.LocationPageInfo
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

    private val _locList : MutableLiveData<List<LocationNameType>> = MutableLiveData()
    val locList : LiveData<List<LocationNameType>>
        get() = _locList

    private val _maxPages : MutableLiveData<Int> = MutableLiveData(0)
    val maxPages : LiveData<Int>
        get() = _maxPages

    private val _currentPage : MutableLiveData<Int> = MutableLiveData(0)
    val currentPage : LiveData<Int>
        get() = _currentPage

    val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadLocationPage(_currentPage.value!!)
    }

    val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadLocationPage(_currentPage.value!!)
    }

    @Suppress("UnstableApiUsage")
    private fun loadLocationPageOne() {

        subscription = apiService.getLocationPageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLocPageStart()  }
            .doOnTerminate { onRetrieveLocPageFinish() }
            .subscribe(
                { result : LocationPageInfo -> onRetrieveLocPageSuccess(result) },
                { onRetrieveLocPageError() }
            )
    }

    @Suppress("UnstableApiUsage")
    private fun loadLocationPage(page : Int) {

        subscription = apiService.getLocationPage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLocPageStart() }
            .doOnTerminate { onRetrieveLocPageFinish() }
            .subscribe(
                { result : LocationPage -> onRetrieveLocationPageSuccess(result) },
                { onRetrieveLocPageError()}
            )
    }

    private fun onRetrieveLocPageStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveLocPageFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveLocPageSuccess(result : LocationPageInfo) {

        _locList.value  = result.results
        _maxPages.value = result.info.pages
        if (result.info.count > 0) _currentPage.value = 1
    }

    private fun onRetrieveLocationPageSuccess(result : LocationPage) {

        _locList.value = result.results
    }

    private fun onRetrieveLocPageError() { }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        loadLocationPageOne()
    }
}