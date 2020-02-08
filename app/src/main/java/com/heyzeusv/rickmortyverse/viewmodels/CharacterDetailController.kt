package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.characterDetail
import com.heyzeusv.rickmortyverse.models.Character
import timber.log.Timber

class CharacterDetailController : TypedEpoxyController<Character>() {

    override fun buildModels(data : Character?) {

        Timber.d(data?.toString() ?: "DEAD")
        data?.let {

            characterDetail {

                id(it.id)
                character(it)
            }
        }
    }
}