package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.CharacterPageInfo
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterPageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var subscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _charList : MutableLiveData< List<CharacterNameImage>> = MutableLiveData()
    val charList : LiveData< List<CharacterNameImage>>
        get() = _charList

    private val _maxPages : MutableLiveData<Int> = MutableLiveData(0)
    val maxPages : LiveData<Int>
        get() = _maxPages

    private val _currentPage : MutableLiveData<Int> = MutableLiveData(0)
    val currentPage : LiveData<Int>
        get() = _currentPage

    val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    @Suppress("UnstableApiUsage")
    private fun loadCharacterPageOne() {

        subscription = apiService.getCharacterPageInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharPageStart()  }
            .doOnTerminate { onRetrieveCharPageFinish() }
            .subscribe(
                { result : CharacterPageInfo -> onRetrieveCharPageSuccess(result) },
                { onRetrieveCharPageError() }
            )
    }

    @Suppress("UnstableApiUsage")
    private fun loadCharacterPage(page : Int) {

        subscription = apiService.getCharacterPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveCharPageStart() }
                .doOnTerminate { onRetrieveCharPageFinish() }
                .subscribe(
                        { result : CharacterPage -> onRetrieveCharacterPageSuccess(result) },
                        { onRetrieveCharPageError()}
                )
    }

    private fun onRetrieveCharPageStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveCharPageFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveCharPageSuccess(result : CharacterPageInfo) {

        _charList.value = result.results
        _maxPages.value = result.info.pages
        if (result.info.count > 0) _currentPage.value = 1
    }

    private fun onRetrieveCharacterPageSuccess(result : CharacterPage) {

        _charList.value = result.results
    }

    private fun onRetrieveCharPageError() { }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        loadCharacterPageOne()
    }
}