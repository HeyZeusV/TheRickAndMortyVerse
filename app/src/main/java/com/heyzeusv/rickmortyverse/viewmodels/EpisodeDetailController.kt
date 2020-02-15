package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.heyzeusv.rickmortyverse.EpisodeCharacterBindingModel_
import com.heyzeusv.rickmortyverse.episodeDetail
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Episode

class EpisodeDetailController : Typed2EpoxyController<Episode, List<CharacterNameImage>>() {

    override fun buildModels(data1 : Episode?, data2 : List<CharacterNameImage>?) {

        data1?.let {

            episodeDetail {

                id("Episode Details")
                episode(it)
            }
        }

        data2?.let {

            val models : List<EpisodeCharacterBindingModel_> = data2.map {
                EpisodeCharacterBindingModel_()
                    .id(it.id)
                    .character(it)
            }
            carousel {

                id("Episode Characters")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(0, 10, 0, 10, 10))
            }
        }
    }
}