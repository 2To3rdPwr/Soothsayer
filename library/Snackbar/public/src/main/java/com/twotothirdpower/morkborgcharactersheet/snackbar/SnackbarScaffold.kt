package com.twotothirdpower.morkborgcharactersheet.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

interface SnackbarScaffold {
    @Composable
    fun Content(content: @Composable (modifier: Modifier) -> Unit)

    companion object {
        val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
            error("No Snackbar Host State")
        }
    }
} 