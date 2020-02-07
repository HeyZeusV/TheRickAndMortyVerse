package com.heyzeusv.rickmortyverse.viewmodels

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.character
import com.heyzeusv.rickmortyverse.models.CharacterPage

class CharacterPageController : TypedEpoxyController<CharacterPage>() {

    private fun setOnClick(id : Int) : View.OnClickListener {

        val bundle : Bundle = bundleOf("characterId" to id)
        return Navigation.createNavigateOnClickListener(
            R.id.action_characterPage_to_characterDetail, bundle)
    }

    override fun buildModels(data : CharacterPage?) {

        data?.results?.forEach {

            character {

               id(it.id)
               character(it)
               onCharClicked(setOnClick(it.id))
            }
        }
    }
}