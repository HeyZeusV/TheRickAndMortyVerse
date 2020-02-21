package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.CharacterPageInfo
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("UnstableApiUsage")
class CharacterPageViewModel : PageViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // subtracts one from _currentPage value and starts request to load new value page
    override val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    // adds one from _currentPage value and starts request to load new value page
    override val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    // starts request to load 1st page if it hasn't been loaded before, else load _currentPage
    override val errorOnClick = View.OnClickListener {

        if (_currentPage.value!! == -1) {

            loadCharacterPageOne()
        } else {

            loadCharacterPage(_currentPage.value!!)
        }
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the first page of Characters.
     */
    private fun loadCharacterPageOne() {

        subscription = apiService.getCharacterPageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : CharacterPageInfo -> onPageOneSuccess(result) },
                { error  : Throwable         -> onError(error)           }
            )
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the page specified of Characters.
     *
     *  @param page the page to be retrieved
     */
    private fun loadCharacterPage(page : Int) {

        subscription = apiService.getCharacterPage(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : CharacterPage -> onPageSuccess(result) },
                { error  : Throwable     -> onError(error)        }
            )
    }

    /**
     *  Ran when first page is requested for the first time.
     *
     *  @param result contains info regarding max pages, as well as a page of Characters
     */
    private fun onPageOneSuccess(result : CharacterPageInfo) {

        _currentPage.value = 1
        _maxPages   .value = result.info.pages
        _dataType   .value = result.results
    }

    /**
     *  @param result contains a page of Characters
     */
    private fun onPageSuccess(result : CharacterPage) { _dataType.value = result.results }

    init {

        loadCharacterPageOne()
    }
}