package com.mehmetalivargun.watchlist.infra

import androidx.lifecycle.ViewModel
import com.mehmetalivargun.watchlist.infra.navigation.Navigation


abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()


}