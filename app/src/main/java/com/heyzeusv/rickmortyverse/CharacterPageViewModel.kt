package com.heyzeusv.rickmortyverse

import androidx.lifecycle.ViewModel
import com.heyzeusv.rickmortyverse.network.ApiService
import javax.inject.Inject

class CharacterPageViewModel : InjectViewModel() {

    @Inject
    lateinit var apiService : ApiService
}