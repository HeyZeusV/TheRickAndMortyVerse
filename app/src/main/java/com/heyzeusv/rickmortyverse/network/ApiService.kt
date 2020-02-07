package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    fun getCharacterPage() : Single<CharacterPage>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id : Int) : Single<Character>
}