package com.heyzeusv.rickmortyverse.models

/**
 *  All classes here correspond to responses from API calls. All variable names are the same as
 *  they appear in API. Should be pretty clear on what each mean...
 *  [Episode Schema](https://rickandmortyapi.com/documentation/#episode-schema)
 */
data class Episode(

    val id         : Int,
    val name       : String,
    val air_date   : String,
    val episode    : String,
    val characters : List<String>
) : FullType()

data class EpisodeNameCode(

    val id      : Int,
    val name    : String,
    val episode : String
) : ShortType()

data class EpisodePage(

    val results : List<EpisodeNameCode>
)

data class EpisodePageInfo(

    val info    : Information,
    val results : List<EpisodeNameCode>
)