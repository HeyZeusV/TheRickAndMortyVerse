package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    fun getCharacterPage() : Single<CharacterPage>

    @GET("character/{charId}")
    fun getCharacter(@Path("charId") charId : Int) : Single<Character>

    @GET("episode/{episodeIds}")
    fun getCharEpisodes(@Path("episodeIds") episodeIds : List<Int>)
            : Single<List<EpisodeNameCode>>
}