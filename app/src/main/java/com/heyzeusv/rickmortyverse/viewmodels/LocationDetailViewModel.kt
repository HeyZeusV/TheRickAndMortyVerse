package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationDetailViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService

    private lateinit var charSubscription : Disposable
    private lateinit var locSubscription  : Disposable

    private val _loadingVisibility : MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility : LiveData<Int>
        get() = _loadingVisibility

    private val _location : MutableLiveData<Location> = MutableLiveData()
    val location : LiveData<Location>
        get() = _location

    private val _locCharacters : MutableLiveData<List<CharacterNameImage>> = MutableLiveData()
    val locCharacters : LiveData<List<CharacterNameImage>>
        get() = _locCharacters

    @Suppress("UnstableApiUsage")
    fun loadLocation(locId : Int) {

        locSubscription = apiService.getLocation(locId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLocDetailStart() }
            .doOnTerminate { onRetrieveLocDetailFinish() }
            .subscribe(
                { result : Location -> onRetrieveLocDetailSuccess(result)},
                { onRetrieveLocDetailError() }
            )
    }

    @Suppress("UnstableApiUsage")
    private fun loadLocCharacters(characterIds : List<Int>) {

        charSubscription = apiService.getCharacters(characterIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLocDetailStart() }
            .doOnTerminate { onRetrieveLocDetailFinish() }
            .subscribe(
                { result : List<CharacterNameImage> -> onRetrieveLocCharacterSuccess(result) },
                { onRetrieveLocDetailError() }
            )
    }

    private fun onRetrieveLocDetailStart() { _loadingVisibility.value = View.VISIBLE }

    private fun onRetrieveLocDetailFinish() { _loadingVisibility.value = View.INVISIBLE }

    private fun onRetrieveLocDetailSuccess(location : Location) {

        _location.value = location

        val characterIds : MutableList<Int> = mutableListOf()
        location.residents.forEach {

            characterIds.add(it.substring(42).toInt())
        }
        loadLocCharacters(characterIds)
    }

    private fun onRetrieveLocDetailError() {}

    private fun onRetrieveLocCharacterSuccess(locCharacters : List<CharacterNameImage>) {

        _locCharacters.value = locCharacters
    }

    override fun onCleared() {
        super.onCleared()

        locSubscription .dispose()
        charSubscription.dispose()
    }
}