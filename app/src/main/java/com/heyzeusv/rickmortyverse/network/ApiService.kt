package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.CharacterPageInfo
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.models.LocationPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    fun getCharacterPageInfo() : Single<CharacterPageInfo>

    @GET("character/?")
    fun getCharacterPage(@Query("page") page : Int) : Single<CharacterPage>

    @GET("character/{charId}")
    fun getCharacter(@Path("charId") charId : Int) : Single<Character>

    @GET("character/{charIds}")
    fun getCharacters(@Path("charIds") charIds : List<Int>)
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

    @GET("location/{locId}")
    fun getLocation(@Path("locId") locId : Int) : Single<Location>
}