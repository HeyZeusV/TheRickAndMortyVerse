package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.models.EpisodePageInfo
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodePageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private val _episList : MutableLiveData<List<EpisodeNameCode>> = MutableLiveData()
    val episList : LiveData<List<EpisodeNameCode>>
        get() = _episList

    private val _maxPages : MutableLiveData<Int> = MutableLiveData(0)
    val maxPages : LiveData<Int>
        get() = _maxPages

    private val _currentPage : MutableLiveData<Int> = MutableLiveData(0)
    val currentPage : LiveData<Int>
        get() = _currentPage

    val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadEpisodePage(_currentPage.value!!)
    }

    val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadEpisodePage(_currentPage.value!!)
    }

    @Suppress("UnstableApiUsage")
    private fun loadEpisodePageOne() {

        subscription =  apiService.getEpisodePageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveEpisPageStart()  }
            .doOnTerminate { onRetrieveEpisPageFinish() }
            .subscribe(
                { result : EpisodePageInfo -> onRetrieveEpisPageSuccess(result) },
                { onRetrieveEpisPageError() }
            )
    }

    @Suppress("UnstableApiUsage")
    private fun loadEpisodePage(page : Int) {

        subscription =  apiService.getEpisodePage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveEpisPageStart()  }
            .doOnTerminate { onRetrieveEpisPageFinish() }
            .subscribe(
                { result : EpisodePage -> onRetrieveEpisodePageSuccess(result) },
                { onRetrieveEpisPageError() }
            )
    }

    private fun onRetrieveEpisPageStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveEpisPageFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveEpisPageSuccess(result : EpisodePageInfo) {

        _episList.value = result.results
        _maxPages.value = result.info.pages
        if (result.info.count > 0) _currentPage.value = 1
    }

    private fun onRetrieveEpisodePageSuccess(result : EpisodePage) {

        _episList.value = result.results
    }

    private fun onRetrieveEpisPageError() { }

    init {

        loadEpisodePageOne()
    }
}