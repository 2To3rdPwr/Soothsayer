package com.twotothirdpower.morkborgcharactersheet.snackbar.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold.Companion.LocalSnackbarHostState
import javax.inject.Inject

class SnackbarScaffoldImpl @Inject constructor() : SnackbarScaffold {
    @Composable
    override fun Content(content: @Composable (modifier: Modifier) -> Unit) {
        SoothsayerTheme {
            val snackbarHostState = remember { SnackbarHostState() }

            CompositionLocalProvider(
                values = arrayOf(
                    LocalSnackbarHostState provides snackbarHostState
                )
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) {
                    content(Modifier.padding(it))
                }
            }
        }
    }
} 