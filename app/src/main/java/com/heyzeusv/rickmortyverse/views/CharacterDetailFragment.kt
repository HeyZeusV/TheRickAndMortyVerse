package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentCharacterDetailBinding
import com.heyzeusv.rickmortyverse.models.Character
import com.heyzeusv.rickmortyverse.models.EpisodeNameCode
import com.heyzeusv.rickmortyverse.viewmodels.CharacterDetailController
import com.heyzeusv.rickmortyverse.viewmodels.CharacterDetailViewModel

class CharacterDetailFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentCharacterDetailBinding

    // EpoxyController
    private val charDetailController = CharacterDetailController()

    // ViewModel
    private val charDetailVM : CharacterDetailViewModel by viewModels()

    // NavController
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?) : View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_character_detail, container, false)
        binding.lifecycleOwner = activity
        binding.charDetailVM   = charDetailVM

        binding.characterDetailEpoxy.layoutManager = LinearLayoutManager(context)
        binding.characterDetailEpoxy.adapter       = charDetailController.adapter

        val charId : Int = arguments?.getInt("characterId") ?: 1
        charDetailVM.loadCharacter(charId)

        charDetailVM.character.observe(viewLifecycleOwner, Observer { character : Character ->

            charDetailVM.charEpisodes.observe(viewLifecycleOwner, Observer {
                    episodes : List<EpisodeNameCode> ->

                charDetailController.setData(character, episodes)
            })
        })

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }
}