package com.heyzeusv.rickmortyverse.models

data class Episode(

    val id : Int
)

data class EpisodeNameCode(

    val id      : Int,
    val name    : String,
    val episode : String
)

data class EpisodePage(

    val results : List<EpisodeNameCode>
)