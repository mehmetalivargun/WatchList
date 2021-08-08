package com.mehmetalivargun.watchlist.infra.snackbar

import com.mehmetalivargun.watchlist.infra.snackbar.SnackbarAction

data class SnackbarState(
    val error: String? = null,
    val errorRes: Int? = null,
    val duration: Int,
    val action: SnackbarAction? = null
) {
    init {
        if (error == null && errorRes == null) {
            throw IllegalArgumentException("You must specify at least one of error or errorRes")
        }
    }
}
