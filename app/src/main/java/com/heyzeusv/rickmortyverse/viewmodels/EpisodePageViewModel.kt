package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodePageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var subscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _episPage : MutableLiveData<EpisodePage> = MutableLiveData()
    val episPage : LiveData<EpisodePage>
        get() = _episPage

    @Suppress("UnstableApiUsage")
    private fun loadEpisodes() {

        subscription =  apiService.getEpisodePage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveEpisPageStart()  }
            .doOnTerminate { onRetrieveEpisPageFinish() }
            .subscribe(
                { result : EpisodePage -> onRetrieveEpisPageSuccess(result) },
                { onRetrieveEpisPageError() }
            )
    }

    private fun onRetrieveEpisPageStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveEpisPageFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveEpisPageSuccess(episPage : EpisodePage) { _episPage.value = episPage }

    private fun onRetrieveEpisPageError() { }

    override fun onCleared() {
        super.onCleared()

        subscription.dispose()
    }

    init {

        loadEpisodes()
    }
}