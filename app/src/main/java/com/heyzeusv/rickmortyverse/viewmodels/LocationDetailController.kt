package com.heyzeusv.rickmortyverse.viewmodels

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

class LocationDetailController : Typed2EpoxyController<Location, List<CharacterNameImage>>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("characterId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_locationDetail_to_character_detail, bundle)
    }

    override fun buildModels(data1 : Location?, data2 : List<CharacterNameImage>?) {

        data1?.let {

            locationDetail {

                id("Location Details")
                location(it)
            }
        }

        data2?.let {

            val models: List<CharacterCarouselBindingModel_> = data2.map {
                CharacterCarouselBindingModel_()
                    .id(it.id)
                    .character(it)
                    .onCharClicked(setOnClick(it.id))
            }
            carousel {

                id("Location Characters")
                models(models)
                numViewsToShowOnScreen(2.5f)
                padding(Carousel.Padding.dp(0, 0, 0, 10, 10))
            }
        }
    }
}