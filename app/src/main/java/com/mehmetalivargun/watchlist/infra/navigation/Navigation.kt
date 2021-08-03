package com.mehmetalivargun.watchlist.infra.navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections

class Navigation {
    private val _navigateTo = MutableLiveData<NavDirections?>()
    val navigateTo: LiveData<NavDirections?> = _navigateTo

    fun navigate(directions: NavDirections) {
        Log.e("Click","Navigate")
        _navigateTo.value = directions

    }

    fun onNavigationComplete() {
        _navigateTo.value = null
    }
}