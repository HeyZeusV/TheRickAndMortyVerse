package com.heyzeusv.rickmortyverse.models

data class Character(

    val id       : Int,
    val name     : String,
    val status   : String,
    val species  : String,
    val type     : String,
    val gender   : String,
    val origin   : Origin,
    val location : CharLocation,
    val image    : String,
    val episode  : List<String>
)

data class Origin(

    val name : String,
    val url  : String
)

data class CharLocation(

    val name : String,
    val url  : String
)

data class CharacterNameImage(

    val id    : Int,
    val name  : String,
    val image : String
)

data class CharacterPage(

    val results : List<CharacterNameImage>
)