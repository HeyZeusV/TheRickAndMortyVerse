package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.CharacterPageInfo
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CharacterPageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private val _charList : MutableLiveData< List<CharacterNameImage>> = MutableLiveData()
    val charList : LiveData< List<CharacterNameImage>>
        get() = _charList

    private val _maxPages : MutableLiveData<Int> = MutableLiveData(0)
    val maxPages : LiveData<Int>
        get() = _maxPages

    private val _currentPage : MutableLiveData<Int> = MutableLiveData(-1)
    val currentPage : LiveData<Int>
        get() = _currentPage

    private val _connectionVisibility : MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)
    val connectionVisibility : LiveData<Int>
        get() = _connectionVisibility

    private val _connectionError : MutableLiveData<Int> = MutableLiveData(0)
    val connectionError : LiveData<Int>
        get() = _connectionError

    val backOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.minus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    val forwardOnClick = View.OnClickListener {

        _currentPage.value = _currentPage.value!!.plus(1)
        loadCharacterPage(_currentPage.value!!)
    }

    val connectionOnClick = View.OnClickListener {

        if (_currentPage.value!! == -1) {

            loadCharacterPageOne()
        } else {

            loadCharacterPage(_currentPage.value!!)
        }
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
                { error -> onRetrieveCharPageError(error) }
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
                        { error : Throwable -> onRetrieveCharPageError(error)}
                )
    }

    private fun onRetrieveCharPageStart() {

        _loadingVisibility.value = View.VISIBLE
        _connectionVisibility.value = View.INVISIBLE
    }

    private fun onRetrieveCharPageFinish() {

        _loadingVisibility.value = View.INVISIBLE
        _connectionVisibility.value = View.INVISIBLE
    }

    private fun onRetrieveCharPageSuccess(result : CharacterPageInfo) {

        _currentPage.value = 1
        _charList.value = result.results
        _maxPages.value = result.info.pages
        if (result.info.count > 0) _currentPage.value = 1
    }

    private fun onRetrieveCharacterPageSuccess(result : CharacterPage) {

        _charList.value = result.results
    }

    private fun onRetrieveCharPageError(error : Throwable) {

        when (error) {

            is UnknownHostException   -> _connectionError.value = 0
            is SocketTimeoutException -> _connectionError.value = 1
            else                      -> _connectionError.value = 2
        }
        _connectionVisibility.value = View.VISIBLE
    }

    init {

        loadCharacterPageOne()
    }
}