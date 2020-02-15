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

    @GET("episode")
    fun getEpisodePage() : Single<EpisodePage>

    @GET("episode/{episId}")
    fun getEpisode(@Path("episId") episId : Int) : Single<Episode>

    @GET("episode/{episodeIds}")
    fun getCharEpisodes(@Path("episodeIds") episodeIds : List<Int>)
            : Single<List<EpisodeNameCode>>
}