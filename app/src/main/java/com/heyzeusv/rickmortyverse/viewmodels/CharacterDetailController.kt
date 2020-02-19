package com.heyzeusv.rickmortyverse.viewmodels

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.heyzeusv.rickmortyverse.CharacterEpisodeBindingModel_
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.characterDetail
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode

class CharacterDetailController : Typed2EpoxyController<Character, List<EpisodeNameCode>>() {

    private fun setEpisodeOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("episodeId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterDetail_to_episodeDetail, bundle)
    }

    private fun setLocationOnClick(url : String) : View.OnClickListener {

        val id : Int = url.substring(41).toInt()
        val bundle : Bundle = bundleOf("locationId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterDetail_to_locationDetail, bundle)
    }

    override fun buildModels(data1 : Character?, data2: List<EpisodeNameCode>?) {

        data1?.let {

            characterDetail {

                id("Character Details")
                character(it)
                if (it.origin.url.isNotEmpty()) originOnClick(setLocationOnClick(it.origin.url))
                if (it.location.url.isNotEmpty()) locationOnClick(setLocationOnClick(it.location.url))
            }
        }

        data2?.let {

            val models : List<CharacterEpisodeBindingModel_> = data2.map {
                CharacterEpisodeBindingModel_()
                    .id(it.id)
                    .episode(it)
                    .onClick(setEpisodeOnClick(it.id))
            }
            carousel {

                id("Character Episodes")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(0, 0, 0, 10, 10))
            }
        }
    }
}