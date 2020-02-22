package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *  ViewModel for EpisodeDetailFragment.
 *
 *  Sets error handling, specific API calls, and what to do on successful calls.
 */
@Suppress("UnstableApiUsage")
class EpisodeDetailViewModel : DetailViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // starts request to load Episode
    override val errorOnClick = View.OnClickListener {

        loadEpisode(typeId)
    }

    /**
     *  REST call using Retrofit that attempts to retrieve specified Episode.
     *
     *  @param episId Episode to be loaded
     */
    fun loadEpisode(episId : Int) {

        typeId = episId

        subscription = apiService.getEpisode(episId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : Episode -> onEpisodeSuccess(result) },
                { error  : Throwable -> onError(error)    }
            )
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the specified Characters.
     *
     *  @param characterIds taken from Episode loaded, Characters in episode
     */
    private fun loadCharacters(characterIds : List<Int>) {

        subscription = apiService.getCharacters(characterIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : List<CharacterNameImage> -> onCharacterSuccess(result) },
                { error  : Throwable -> onError(error)    }
            )
    }

    /**
     *  Retrieves Character ids from list of characters in Episode, by removing most of the URL
     *  leaving only the id.
     *
     *  @param episode Episode that was loaded
     */
    private fun onEpisodeSuccess(episode : Episode) {

        _dataType.value = episode

        val characterIds : MutableList<Int> = mutableListOf()
        episode.characters.forEach {

            characterIds.add(it.substring(42).toInt())
        }
        loadCharacters(characterIds)
    }

    /**
     *  @param characters characters in loaded Episode
     */
    private fun onCharacterSuccess(characters : List<CharacterNameImage>) {

        _carouselType.value = characters
    }
}