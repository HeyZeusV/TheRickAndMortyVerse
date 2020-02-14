package com.heyzeusv.rickmortyverse.viewmodels


import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.episode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import timber.log.Timber

class EpisodePageController : TypedEpoxyController<EpisodePage>() {

    override fun buildModels(data : EpisodePage?) {

        data?.results?.forEach {

            Timber.d(it.toString())
            episode {

                id(it.id)
                episode(it)
            }
        }
    }
}