package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CharacterDetailViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var charSubscription : Disposable
    private lateinit var epiSubscription  : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _charDetail : MutableLiveData<Character> = MutableLiveData()
    val charDetail : LiveData<Character>
        get() = _charDetail

    private val _charEpisodes : MutableLiveData<List<EpisodeNameCode>> = MutableLiveData()
    val charEpisodes : LiveData<List<EpisodeNameCode>>
        get() = _charEpisodes

    @Suppress("UnstableApiUsage")
    fun loadCharacter(charId : Int) {

        charSubscription = apiService.getCharacter(charId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharDetailStart()  }
            .doOnTerminate { onRetrieveCharDetailFinish() }
            .subscribe(
                { result : Character -> onRetrieveCharDetailSuccess(result) },
                { error  : Throwable -> onRetrieveCharDetailError(error)    }
            )
    }

    @Suppress("UnstableApiUsage")
    private fun loadCharEpisodes(episodeIds : List<Int>) {

        epiSubscription = apiService.getCharEpisodes(episodeIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCharDetailStart() }
            .doOnTerminate { onRetrieveCharDetailFinish() }
            .subscribe(
                { result : List<EpisodeNameCode> -> onRetrieveCharEpisodeSuccess(result)},
                { error  : Throwable             -> onRetrieveCharDetailError(error)    }
            )
    }

    private fun onRetrieveCharDetailStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveCharDetailFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveCharDetailSuccess(charDetail : Character) {

        _charDetail.value = charDetail

        val episodeIds : MutableList<Int> = mutableListOf()
        charDetail.episode.forEach {

            episodeIds.add(it.substring(40).toInt())
        }
        loadCharEpisodes(episodeIds)
    }

    private fun onRetrieveCharDetailError(error : Throwable) {
        Timber.d(error.toString())
    }

    private fun onRetrieveCharEpisodeSuccess(charEpisodes : List<EpisodeNameCode>) {

        _charEpisodes.value = charEpisodes
    }

    override fun onCleared() {
        super.onCleared()

        charSubscription.dispose()
        epiSubscription .dispose()
    }
}