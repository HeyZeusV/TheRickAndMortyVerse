package com.heyzeusv.rickmortyverse.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.heyzeusv.rickmortyverse.R
import com.heyzeusv.rickmortyverse.databinding.FragmentCharacterDetailBinding
import com.heyzeusv.rickmortyverse.viewmodels.CharacterDetailViewModel
import timber.log.Timber

class CharacterDetailFragment : Fragment() {

    // DataBinding
    private lateinit var binding : FragmentCharacterDetailBinding

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

        val charId : Int = arguments?.getInt("characterId") ?: 1
        charDetailVM.loadCharacter(charId)

        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }
}