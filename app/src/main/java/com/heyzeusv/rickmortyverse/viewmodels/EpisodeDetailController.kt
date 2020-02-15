package com.heyzeusv.rickmortyverse.viewmodels

import com.airbnb.epoxy.Typed2EpoxyController
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
    }
}