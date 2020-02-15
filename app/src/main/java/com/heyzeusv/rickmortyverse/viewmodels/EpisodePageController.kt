package com.heyzeusv.rickmortyverse.viewmodels


import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.episode
import com.heyzeusv.rickmortyverse.models.EpisodePage
import timber.log.Timber

class EpisodePageController : TypedEpoxyController<EpisodePage>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("episodeId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_episodePage_to_episodeDetail, bundle)
    }

    override fun buildModels(data : EpisodePage?) {

        data?.results?.forEach {

            episode {

                id(it.id)
                episode(it)
                onEpisClicked(setOnClick(it.id))
            }
        }
    }
}