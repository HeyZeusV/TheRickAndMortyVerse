package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeDetailViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var episSubscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _episode : MutableLiveData<Episode> = MutableLiveData()
    val episode : LiveData<Episode>
        get() = _episode

    @Suppress("UnstableApiUsage")
    fun loadEpisode(episId : Int) {

        episSubscription = apiService.getEpisode(episId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveEpisDetailStart()  }
            .doOnTerminate { onRetrieveEpisDetailFinish() }
            .subscribe(
                { result : Episode -> onRetrieveEpisDetailSuccess(result) },
                { onRetrieveEpisDetailError() }
            )
    }

    private fun onRetrieveEpisDetailStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveEpisDetailFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveEpisDetailSuccess(episode : Episode) {

        _episode.value = episode
    }

    private fun onRetrieveEpisDetailError() {}

    override fun onCleared() {
        super.onCleared()

        episSubscription.dispose()
    }
}