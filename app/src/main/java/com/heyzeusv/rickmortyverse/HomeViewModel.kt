package com.heyzeusv.rickmortyverse

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation

class HomeViewModel : ViewModel() {

    val characterOnClick : View.OnClickListener =
        Navigation.createNavigateOnClickListener(R.id.action_home_to_characterPage)
}