package com.heyzeusv.rickmortyverse.controllers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.character
import com.heyzeusv.rickmortyverse.models.CharacterNameImage

class CharacterPageController : TypedEpoxyController< List<CharacterNameImage>>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("characterId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterPage_to_characterDetail, bundle)
    }

    override fun buildModels(data :  List<CharacterNameImage>?) {

        data?.forEach {

            character {

               id(it.id)
               character(it)
               onCharClicked(setOnClick(it.id))
            }
        }
    }
}