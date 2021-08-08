package com.mehmetalivargun.watchlist.infra.snackbar
import androidx.annotation.StringRes

data class SnackbarAction(
    @StringRes
    val text: Int,
    val action: () -> Unit
)
