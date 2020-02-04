package com.heyzeusv.rickmortyverse.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.heyzeusv.rickmortyverse.R

class HomeViewModel : ViewModel() {

    val characterOnClick : View.OnClickListener =
        Navigation.createNavigateOnClickListener(R.id.action_home_to_characterPage)
}