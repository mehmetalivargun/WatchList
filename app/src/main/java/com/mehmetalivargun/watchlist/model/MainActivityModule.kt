package com.mehmetalivargun.watchlist.model

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mehmetalivargun.watchlist.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.fragmentContainerView)
    }
}