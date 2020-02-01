package com.heyzeusv.rickmortyverse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CharacterListFragment : Fragment() {

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?) : View? {

        val view : View =
            inflater.inflate(R.layout.fragment_character_list, container, false)
        
        return view
    }
}