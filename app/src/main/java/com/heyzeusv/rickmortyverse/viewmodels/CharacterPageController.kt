package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.character
import com.heyzeusv.rickmortyverse.models.CharacterPage

class CharacterPageController : TypedEpoxyController<CharacterPage>() {

    override fun buildModels(data: CharacterPage?) {

        data?.results?.forEach {

            character {

               id(it.id)
               character(it)

            }
        }
    }
}