package com.twotothirdpower.morkborgcharactersheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackbarScaffold: SnackbarScaffold

    @Inject
    lateinit var navigationGraph: NavigationGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoothsayerTheme {
                snackbarScaffold.Content {
                    MainContent(
                        navigationGraph = navigationGraph
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    navigationGraph: NavigationGraph
) {
    val navController = rememberNavController()

    navigationGraph.Content(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}
