package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CharacterPageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var subscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _charPage : MutableLiveData<CharacterPage> = MutableLiveData()
    val charPage : LiveData<CharacterPage>
        get() = _charPage

    @Suppress("UnstableApiUsage")
    private fun loadCharacters() {

        subscription = apiService.getCharacterPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharPageStart() }
            .doOnTerminate { onRetrieveCharPageFinish() }
            .subscribe(
                { result -> onRetrieveCharPageSuccess(result) },
                { onRetrieveCharPageError() }
            )
    }

    private fun onRetrieveCharPageStart() {

        _loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveCharPageFinish() {

        _loadingVisibility.value = View.INVISIBLE
        Timber.v("HERE!")
    }

    private fun onRetrieveCharPageSuccess(charPage : CharacterPage) {

        _charPage.value = charPage
    }

    private fun onRetrieveCharPageError() {


    }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        loadCharacters()
    }
}