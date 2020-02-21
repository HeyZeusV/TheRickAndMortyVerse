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

class EpisodePageViewModel : PageViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // holds one page of Episodes (20 max)
    private val _episList : MutableLiveData<List<EpisodeNameCode>> = MutableLiveData()
    val episList : LiveData<List<EpisodeNameCode>>
        get() = _episList

    // subtracts one from _currentPage value and starts request to load new value page
    override val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadEpisodePage(_currentPage.value!!)
    }

    // adds one from _currentPage value and starts request to load new value page
    override val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadEpisodePage(_currentPage.value!!)
    }

    // starts request to load 1st page if it hasn't been loaded before, else load _currentPage
    override val errorOnClick = View.OnClickListener {

        if (_currentPage.value!! == -1) {

            loadEpisodePageOne()
        } else {

            loadEpisodePage(_currentPage.value!!)
        }
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the first page of Episodes.
     */
    @Suppress("UnstableApiUsage")
    private fun loadEpisodePageOne() {

        subscription =  apiService.getEpisodePageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : EpisodePageInfo -> onPageOneSuccess(result) },
                { error  : Throwable       -> onError(error)           }
            )
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the page specified of Episodes.
     *
     *  @param page the page to be retrieved
     */
    @Suppress("UnstableApiUsage")
    private fun loadEpisodePage(page : Int) {

        subscription =  apiService.getEpisodePage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : EpisodePage -> onPageSuccess(result) },
                { error  : Throwable   -> onError(error)        }
            )
    }

    /**
     *  Ran when first page is requested for the first time.
     *
     *  @param result contains info regarding max pages, as well as a page of Episodes
     */
    private fun onPageOneSuccess(result : EpisodePageInfo) {

        _currentPage.value = 1
        _maxPages   .value = result.info.pages
        _episList   .value = result.results

    }

    /**
     *  @param result contains a page of Characters
     */
    private fun onPageSuccess(result : EpisodePage) { _episList.value = result.results }

    init {

        loadEpisodePageOne()
    }
}