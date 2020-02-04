package com.heyzeusv.rickmortyverse

import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("character/")
    fun getCharacterPage() : Single<List<Character>>
}