package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.character
import com.heyzeusv.rickmortyverse.models.CharacterNameImage

/**
 *  Epoxy Controller used to build models using layouts with "item_view" prefix.
 *
 *  @property List<CharacterNameImage> primary DataType to be displayed
 */
class CharacterPageController : TypedEpoxyController<List<CharacterNameImage>>() {

    // navigate user to Character selected
    private fun setCharacterOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("characterId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterPage_to_characterDetail, bundle)
    }

    /**
     *  Describes what models should be shown for the current state.
     *
     *  @param data primary data to be shown
     */
    override fun buildModels(data :  List<CharacterNameImage>?) {

        data?.forEach {

            // uses item_view_character.xml as model and sets up data to variables in layout
            character {

               id(it.id)
               character(it)
               onCharClicked(setCharacterOnClick(it.id))
            }
        }
    }
}