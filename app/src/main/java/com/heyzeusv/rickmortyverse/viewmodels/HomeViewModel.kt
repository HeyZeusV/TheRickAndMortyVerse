package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.heyzeusv.rickmortyverse.R

/**
 *  Basic ViewModel, only contains onClicks for Buttons.
 */
class HomeViewModel : ViewModel() {

    // navigates user to CharacterPageFragment
    val characterOnClick : View.OnClickListener =
        Navigation.createNavigateOnClickListener(R.id.action_home_to_characterPage)

    // navigates user to EpisodePageFragment
    val episodeOnClick : View.OnClickListener =
        Navigation.createNavigateOnClickListener(R.id.action_home_to_episodePage)

    // navigates user to LocationPageFragment
    val locationOnClick : View.OnClickListener =
        Navigation.createNavigateOnClickListener(R.id.action_home_to_location_page)
}