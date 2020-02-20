package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.episode
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode

class EpisodePageController : TypedEpoxyController<List<EpisodeNameCode>>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("episodeId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_episodePage_to_episodeDetail, bundle)
    }

    override fun buildModels(data : List<EpisodeNameCode>?) {

        data?.forEach {

            episode {

                id(it.id)
                episode(it)
                onEpisClicked(setOnClick(it.id))
            }
        }
    }
}