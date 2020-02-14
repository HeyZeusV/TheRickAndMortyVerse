package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.heyzeusv.rickmortyverse.CharacterEpisodeBindingModel_
import com.heyzeusv.rickmortyverse.characterDetail
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode

class CharacterDetailController : Typed2EpoxyController<Character, List<EpisodeNameCode>>() {

    override fun buildModels(data1 : Character?, data2: List<EpisodeNameCode>?) {

        data1?.let {

            characterDetail {

                id("Character Details")
                character(it)
            }
        }

        data2?.let {

            val models : List<CharacterEpisodeBindingModel_> = data2.map {
                CharacterEpisodeBindingModel_()
                    .id(it.id)
                    .episode(it)
            }
            carousel {

                id("Character Episodes")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(0, 10, 0, 10, 10))
            }
        }
    }
}