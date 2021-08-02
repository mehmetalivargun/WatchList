package com.mehmetalivargun.watchlist.infra

import androidx.lifecycle.ViewModel
import com.mehmetalivargun.movierecomandation.Navigation


abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()


}