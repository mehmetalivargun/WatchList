package com.mehmetalivargun.watchlist.infra.navigation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

class NavigationObserver {
    fun observe(
        navigation: Navigation,
        navController: NavController,
        lifecycleOwner: LifecycleOwner
    ) {
        navigation.navigateTo.observe(lifecycleOwner) { directions ->
            directions?.let {
                navController.navigate(it)
                Log.e("Move","Burda")
                navigation.onNavigationComplete()
            }
        }
    }
}