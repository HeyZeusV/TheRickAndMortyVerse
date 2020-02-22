package com.heyzeusv.rickmortyverse.models

/*
 *  All classes here correspond to responses from API calls. All variable names are the same as
 *  they appear in API. Should be pretty clear on what each mean...
 *  [Character Schema](https://rickandmortyapi.com/documentation/#character-schema)
 */
data class Character(

    val id       : Int,
    val name     : String,
    val status   : String,
    val species  : String,
    val type     : String,
    val gender   : String,
    val origin   : Origin,
    val location : CurrentLocation,
    val image    : String,
    val episode  : List<String>
) : FullType()

data class Origin(

    val name : String,
    val url  : String
)

data class CurrentLocation(

    val name : String,
    val url  : String
)

data class CharacterNameImage(

    val id    : Int,
    val name  : String,
    val image : String
) : ShortType()

data class CharacterPage(

    val results : List<CharacterNameImage>
)

data class CharacterPageInfo(

    val info    : Information,
    val results : List<CharacterNameImage>
)