package com.mehmetalivargun.watchlist.infra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehmetalivargun.watchlist.infra.navigation.Navigation
import com.mehmetalivargun.watchlist.infra.snackbar.SnackbarState


abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()
    protected val _snackbarState = MutableLiveData<SnackbarState>()
    val snackbarState: LiveData<SnackbarState> = _snackbarState

}