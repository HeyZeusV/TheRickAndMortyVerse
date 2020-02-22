package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.episode
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode

/**
 *  Epoxy Controller used to build models using layouts with "item_view" prefix.
 *
 *  @property List<EpisodeNameCode> primary DataType to be displayed
 */
class EpisodePageController : TypedEpoxyController<List<EpisodeNameCode>>() {

    // navigate user to Episode selected
    private fun setEpisodeOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("episodeId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_episodePage_to_episodeDetail, bundle)
    }

    /**
     *  Describes what models should be shown for the current state.
     *
     *  @param data primary data to be shown
     */
    override fun buildModels(data : List<EpisodeNameCode>?) {

        data?.forEach {

            // uses item_view_episode.xml as model and sets up data to variables in layout
            episode {

                id(it.id)
                episode(it)
                onEpisClicked(setEpisodeOnClick(it.id))
            }
        }
    }
}