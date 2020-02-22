package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *  ViewModel for LocationDetailFragment.
 *
 *  Sets error handling, specific API calls, and what to do on successful calls.
 */
@Suppress("UnstableApiUsage")
class LocationDetailViewModel : DetailViewModel() {

    @Inject
    lateinit var apiService : ApiService

    // starts request to load Location
    override val errorOnClick = View.OnClickListener {

        loadLocation(typeId)
    }

    /**
     *  REST call using Retrofit that attempts to retrieve specified Location.
     *
     *  @param locId Location to be loaded
     */
    fun loadLocation(locId : Int) {

        typeId = locId

        subscription = apiService.getLocation(locId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : Location  -> onLocationSuccess(result)},
                { error  : Throwable -> onError(error)           }
            )
    }

    /**
     *  REST call using Retrofit that attempts to retrieve the specified Characters.
     *
     *  @param characterIds taken from Location loaded, residents of Location
     */
    private fun loadResidents(characterIds : List<Int>) {

        subscription = apiService.getCharacters(characterIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe(
                { result : List<CharacterNameImage> -> onResidentSuccess(result) },
                { error  : Throwable                -> onError(error)            }
            )
    }

    /**
     *  Retrieves Character ids from list of residents in Location, by removing most of the URL
     *  leaving only the id.
     *
     *  @param location Location that was loaded
     */
    private fun onLocationSuccess(location : Location) {

        _dataType.value = location

        val characterIds : MutableList<Int> = mutableListOf()
        location.residents.forEach {

            characterIds.add(it.substring(42).toInt())
        }
        loadResidents(characterIds)
    }

    /**
     *  @param residents residents of loaded Location
     */
    private fun onResidentSuccess(residents : List<CharacterNameImage>) {

        _carouselType.value = residents
    }
}