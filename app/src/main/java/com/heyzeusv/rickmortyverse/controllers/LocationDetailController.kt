package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.carousel
import com.heyzeusv.rickmortyverse.CharacterCarouselBindingModel_
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.locationDetail
import com.heyzeusv.rickmortyverse.models.CharacterNameImage
import com.heyzeusv.rickmortyverse.models.Location

/**
 *  Epoxy Controller used to build models using layouts with "item_view" prefix.
 *
 *  @property Location primary DataType to be displayed
 *  @property List<CharacterNameImage> DataType to be displayed in Carousel
 */
class LocationDetailController : Typed2EpoxyController<Location, List<CharacterNameImage>>() {

    // navigate user to selected Character in Carousel
    private fun setCharacterOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("characterId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_locationDetail_to_character_detail, bundle)
    }

    /**
     *  Describes what models should be shown for the current state.
     *
     *  @param dataType     primary data to be shown in details section
     *  @param carouselData data to be shown in carousel section
     */
    override fun buildModels(dataType : Location?, carouselData : List<CharacterNameImage>?) {

        dataType?.let {

            // uses item_view_location_detail.xml as model and sets up data to variables in layout
            locationDetail {

                id("Location Details")
                location(it)
            }
        }

        carouselData?.let {

            // builds models for carousel using item_view_character_carousel.xml
            val models: List<CharacterCarouselBindingModel_> = carouselData.map {
                CharacterCarouselBindingModel_()
                    .id(it.id)
                    .character(it)
                    .onCharClicked(setCharacterOnClick(it.id))
            }

            // builds carousel using models made above and sets spacing and size
            carousel {

                id("Location Characters")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(0, 0, 0, 10, 10))
            }
        }
    }
}