package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getCharacterPage() : Single<List<Character>>
}