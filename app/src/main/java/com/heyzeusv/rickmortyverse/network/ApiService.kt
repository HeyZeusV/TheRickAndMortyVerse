package com.heyzeusv.rickmortyverse.network

import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.CharacterPage
import com.heyzeusv.rickmortyverse.models.CharacterPageInfo
import com.heyzeusv.rickmortyverse.models.Episode
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import com.heyzeusv.rickmortyverse.models.EpisodePageInfo
import com.heyzeusv.rickmortyverse.models.Location
import com.heyzeusv.rickmortyverse.models.LocationPage
import com.heyzeusv.rickmortyverse.models.LocationPageInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  Calls made to API.
 *
 *  All return Single<T> since data is not changing and only needs to be emitted once.
 */
interface ApiService {

    /**
     *  @return first page of Characters including page information
     */
    @GET("character")
    fun getCharacterPageInfo() : Single<CharacterPageInfo>

    /**
     *  @param  page page to return
     *  @return page of Characters selected without page information
     */
    @GET("character/?")
    fun getCharacterPage(@Query("page") page : Int) : Single<CharacterPage>

    /**
     *  @param  charId id of Character to return
     *  @return Character selected
     */
    @GET("character/{charId}")
    fun getCharacter(@Path("charId") charId : Int) : Single<Character>

    /**
     *  @param  charIds list of Character ids to return
     *  @return list of Characters with limited details
     */
    @GET("character/{charIds}")
    fun getCharacters(@Path("charIds") charIds : List<Int>)
            : Single<List<CharacterNameImage>>

    /**
     *  @return first page of Episodes including page information
     */
    @GET("episode")
    fun getEpisodePageInfo() : Single<EpisodePageInfo>

    /**
     *  @param  page page to return
     *  @return page of Episodes selected without page information
     */
    @GET("episode/?")
    fun getEpisodePage(@Query("page") page : Int) : Single<EpisodePage>

    /**
     *  @param  episId id of Episode to return
     *  @return Episode selected
     */
    @GET("episode/{episId}")
    fun getEpisode(@Path("episId") episId : Int) : Single<Episode>

    /**
     *  @param  episIds list of Episode ids to return
     *  @return list of Episodes with limited details
     */
    @GET("episode/{episIds}")
    fun getCharEpisodes(@Path("episIds") episIds : List<Int>)
            : Single<List<EpisodeNameCode>>

    /**
     *  @return first page of Locations including page information
     */
    @GET("location")
    fun getLocationPageInfo() : Single<LocationPageInfo>

    /**
     *  @param  page page to return
     *  @return page of Locations selected without page information
     */
    @GET("location/?")
    fun getLocationPage(@Query("page") page : Int) : Single<LocationPage>

    /**
     *  @param  locId id of Location to return
     *  @return Location selected
     */
    @GET("location/{locId}")
    fun getLocation(@Path("locId") locId : Int) : Single<Location>
}