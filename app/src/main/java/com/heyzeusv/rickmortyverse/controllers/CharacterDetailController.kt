package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.heyzeusv.rickmortyverse.EpisodeCarouselBindingModel_
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.characterDetail
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode

/**
 *  Epoxy Controller used to build models using layouts with "item_view" prefix.
 *
 *  @property Character primary DataType to be displayed
 *  @property List<EpisodeNameCode> DataType to be displayed in Carousel
 */
class CharacterDetailController : Typed2EpoxyController<Character, List<EpisodeNameCode>>() {

    // navigate user to selected Episode in Carousel
    private fun setEpisodeOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("episodeId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterDetail_to_episodeDetail, bundle)
    }

    // navigate user to selected Location in details section
    private fun setLocationOnClick(url : String) : View.OnClickListener {

        val id     : Int    = url.substring(41).toInt()
        val bundle : Bundle = bundleOf("locationId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterDetail_to_locationDetail, bundle)
    }

    /**
     *  Describes what models should be shown for the current state.
     *
     *  @param dataType     primary data to be shown in details section
     *  @param carouselData data to be shown in carousel section
     */
    override fun buildModels(dataType : Character?, carouselData: List<EpisodeNameCode>?) {

        dataType?.let {

            // uses item_view_character_detail.xml as model and sets up data to variables in layout
            characterDetail {

                id("Character Details")
                character(it)
                if (it.origin.url.isNotEmpty()  ) originOnClick(setLocationOnClick(it.origin.url))
                if (it.location.url.isNotEmpty()) locationOnClick(setLocationOnClick(it.location.url))
            }
        }

        carouselData?.let {

            // builds models for carousel using item_view_episode_carousel.xml
            val models : List<EpisodeCarouselBindingModel_> = carouselData.map {
                EpisodeCarouselBindingModel_()
                    .id(it.id)
                    .episode(it)
                    .onClick(setEpisodeOnClick(it.id))
            }

            // builds carousel using models made above and sets spacing and size
            carousel {

                id("Character Episodes")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(-4, -4, -4, 2, 2))
            }
        }
    }
}