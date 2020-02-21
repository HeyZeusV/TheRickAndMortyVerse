package com.heyzeusv.rickmortyverse.models

/**
 *  All classes here correspond to responses from API calls. All variable names are the same as
 *  they appear in API. Should be pretty clear on what each mean...
 *  [Location Schema](https://rickandmortyapi.com/documentation/#location-schema)
 */
data class Location(

    val id        : Int,
    val name      : String,
    val type      : String,
    val dimension : String,
    val residents : List<String>
) : FullType()

data class LocationNameType(

    val id   : Int,
    val name : String,
    val type : String
) : ShortType()

data class LocationPage(

    val results : List<LocationNameType>
)

data class LocationPageInfo(

    val info    : Information,
    val results : List<LocationNameType>
)