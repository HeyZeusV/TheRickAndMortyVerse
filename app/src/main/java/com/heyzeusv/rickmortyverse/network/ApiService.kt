package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    fun getCharacterPage() : Single<CharacterPage>

    @GET("character/{charId}")
    fun getCharacter(@Path("charId") charId : Int) : Single<Character>

    @GET("character/{charIds}")
    fun getEpisCharacters(@Path("charIds") charIds : List<Int>)
            : Single<List<CharacterNameImage>>

    @GET("episode")
    fun getEpisodePage() : Single<EpisodePage>

    @GET("episode/{episId}")
    fun getEpisode(@Path("episId") episId : Int) : Single<Episode>

    @GET("episode/{episIds}")
    fun getCharEpisodes(@Path("episIds") episIds : List<Int>)
            : Single<List<EpisodeNameCode>>
}