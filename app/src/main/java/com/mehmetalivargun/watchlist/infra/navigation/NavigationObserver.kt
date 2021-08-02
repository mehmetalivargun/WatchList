package com.mehmetalivargun.movierecomandation.infra.navigation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.mehmetalivargun.movierecomandation.Navigation

class NavigationObserver {
    fun observe(
        navigation: Navigation,
        navController: NavController,
        lifecycleOwner: LifecycleOwner
    ) {
        navigation.navigateTo.observe(lifecycleOwner) { directions ->
            directions?.let {
                navController.navigate(it)
                Log.d("Move","Burda")
                navigation.onNavigationComplete()
            }
        }
    }
}