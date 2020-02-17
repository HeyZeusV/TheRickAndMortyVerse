package com.heyzeusv.rickmortyverse.models

data class Location(

    val id        : Int,
    val name      : String,
    val type      : String,
    val dimension : String,
    val residents : List<String>
)

data class LocationNameType(

    val id   : Int,
    val name : String,
    val type : String
)

data class LocationPage(

    val results : List<LocationNameType>
)

