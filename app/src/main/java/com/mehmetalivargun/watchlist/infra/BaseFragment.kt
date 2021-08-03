package com.mehmetalivargun.watchlist.infra

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mehmetalivargun.watchlist.infra.navigation.NavigationObserver


abstract class BaseFragment(fragmentMovieList: Int) : Fragment() {

    abstract val viewModel: BaseViewModel
    private val navigationObserver = NavigationObserver()


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationObserver.observe(viewModel.navigation, findNavController(), viewLifecycleOwner)
    }


}