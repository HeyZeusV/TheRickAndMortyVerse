package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CharacterDetailViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var subscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _charDetail : MutableLiveData<Character> = MutableLiveData()
    val charDetail : LiveData<Character>
        get() = _charDetail


    @Suppress("UnstableApiUsage")
    fun loadCharacter(charId : Int) {

        subscription = apiService.getCharacter(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveCharDetailStart()  }
                .doOnTerminate { onRetrieveCharDetailFinish() }
                .subscribe(
                        { result : Character -> onRetrieveCharDetailSuccess(result) },
                        { onRetrieveCharDetailError() }
                )
    }

    private fun onRetrieveCharDetailStart() {

        _loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveCharDetailFinish() {

        _loadingVisibility.value = View.INVISIBLE
    }

    private fun onRetrieveCharDetailSuccess(charDetail : Character) {

        _charDetail.value = charDetail
        Timber.d(_charDetail.value.toString())
    }

    private fun onRetrieveCharDetailError() {


    }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }
}