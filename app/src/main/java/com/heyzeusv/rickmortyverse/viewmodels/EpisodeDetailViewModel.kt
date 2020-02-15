package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EpisodeDetailViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var charSubscription : Disposable
    private lateinit var episSubscription : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _episode : MutableLiveData<Episode> = MutableLiveData()
    val episode : LiveData<Episode>
        get() = _episode

    private val _episCharacters : MutableLiveData<List<CharacterNameImage>> = MutableLiveData()
    val episCharacters : LiveData<List<CharacterNameImage>>
        get() = _episCharacters

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

    @Suppress("UnstableApiUsage")
    private fun loadEpisCharacters(characterIds : List<Int>) {

        charSubscription = apiService.getEpisCharacters(characterIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveEpisDetailStart() }
            .doOnTerminate { onRetrieveEpisDetailFinish() }
            .subscribe(
                { result : List<CharacterNameImage> -> onRetrieveEpisCharacterSuccess(result) },
                { onRetrieveEpisDetailError() }
            )
    }

    private fun onRetrieveEpisDetailStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveEpisDetailFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveEpisDetailSuccess(episode : Episode) {

        _episode.value = episode

        val characterIds : MutableList<Int> = mutableListOf()
        episode.characters.forEach {

            characterIds.add(it.substring(42).toInt())
        }
        loadEpisCharacters(characterIds)
    }

    private fun onRetrieveEpisDetailError() {}

    private fun onRetrieveEpisCharacterSuccess(episCharacters : List<CharacterNameImage>) {

        _episCharacters.value = episCharacters
    }

    override fun onCleared() {
        super.onCleared()

        episSubscription.dispose()
    }
}