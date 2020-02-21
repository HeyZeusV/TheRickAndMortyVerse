package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("UnstableApiUsage")
class CharacterDetailViewModel : DetailViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // starts request to load Character
    override val errorOnClick = View.OnClickListener {

        loadCharacter(typeId)
    }

    /**
     *  REST call using Retrofit that attempts to retrieve specified Character.
     *
     *  @param charId Character to be loaded
     */
    fun loadCharacter(charId : Int) {

        typeId = charId

        subscription = apiService.getCharacter(charId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart()  }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : Character -> onCharacterSuccess(result) },
                { error  : Throwable -> onError(error)             }
            )
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the specified Episodes.
     *
     *  @param episodeIds taken from Character loaded, episodes Character appears in
     */
    private fun loadEpisodes(episodeIds : List<Int>) {

        subscription = apiService.getCharEpisodes(episodeIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : List<EpisodeNameCode> -> onEpisodeSuccess(result) },
                { error  : Throwable             -> onError(error)           }
            )
    }

    /**
     *  Retrieves Episode ids from list of episode in Character, by removing most of the URL
     *  leaving only the id.
     *
     *  @param character Character that was loaded
     */
    private fun onCharacterSuccess(character : Character) {

        _dataType.value = character

        val episodeIds : MutableList<Int> = mutableListOf()
        character.episode.forEach {

            episodeIds.add(it.substring(40).toInt())
        }
        loadEpisodes(episodeIds)
    }

    /**
     *  @param episodes episodes that loaded Character appears in
     */
    private fun onEpisodeSuccess(episodes : List<EpisodeNameCode>) {

        _carouselType.value = episodes
    }
}