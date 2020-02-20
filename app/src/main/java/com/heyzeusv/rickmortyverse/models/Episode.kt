package com.heyzeusv.rickmortyverse.models

data class Episode(

    val id         : Int,
    val name       : String,
    val air_date   : String,
    val episode    : String,
    val characters : List<String>
)

data class EpisodeNameCode(

    val id      : Int,
    val name    : String,
    val episode : String
)

data class EpisodePage(

    val results : List<EpisodeNameCode>
)

data class EpisodePageInfo(

    val info    : Information,
    val results : List<EpisodeNameCode>
)