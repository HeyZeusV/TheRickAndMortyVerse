package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.models.LocationPage
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

    @GET("location")
    fun getLocationPage() : Single<LocationPage>
}