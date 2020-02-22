package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.LocationNameType
import com.heyzeusv.rickmortyverse.models.LocationPage
import com.heyzeusv.rickmortyverse.models.LocationPageInfo
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *  ViewModel for LocationPageFragment.
 *
 *  Sets error handling and back/forward buttons, specific API calls,
 *  and what to do on successful calls.
 */
@Suppress("UnstableApiUsage")
class LocationPageViewModel : PageViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // subtracts one from _currentPage value and starts request to load new value page
    override val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadLocationPage(_currentPage.value!!)
    }

    // adds one from _currentPage value and starts request to load new value page
    override val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadLocationPage(_currentPage.value!!)
    }

    // starts request to load 1st page if it hasn't been loaded before, else load _currentPage
    override val errorOnClick = View.OnClickListener {

        if (_currentPage.value!! == -1) {

            loadLocationPageOne()
        } else {

            loadLocationPage(_currentPage.value!!)
        }
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the first page of Locations.
     */
    private fun loadLocationPageOne() {

        subscription = apiService.getLocationPageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : LocationPageInfo -> onPageOneSuccess(result) },
                { error  : Throwable        -> onError(error)                   }
            )
    }


    /**
     *  REST call using Retrofit that attempts to retrieve the page specified of Locations.
     *
     *  @param page the page to be retrieved
     */
    private fun loadLocationPage(page : Int) {

        subscription = apiService.getLocationPage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : LocationPage -> onPageSuccess(result) },
                { error  : Throwable    -> onError(error)                        }
            )
    }

    /**
     *  Ran when first page is requested for the first time.
     *
     *  @param result contains info regarding max pages, as well as a page of Locations
     */
    private fun onPageOneSuccess(result : LocationPageInfo) {

        _currentPage.value = 1
        _maxPages   .value = result.info.pages
        _dataType    .value = result.results
    }

    /**
     *  @param result contains a page of Locations
     */
    private fun onPageSuccess(result : LocationPage) { _dataType.value = result.results }

    init {

        loadLocationPageOne()
    }
}