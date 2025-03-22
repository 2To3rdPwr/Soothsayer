package com.twotothirdpower.morkborgcharactersheet

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.OldNewspaper
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold.Companion.LocalSnackbarHostState
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackbarScaffold: SnackbarScaffold

    @Inject
    lateinit var navigationGraph: NavigationGraph

    @Inject
    lateinit var topNavBar: TopNavBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            SoothsayerTheme {
                snackbarScaffold.Content { modifier ->
                    MainContent(
                        modifier = modifier,
                        navigationGraph = navigationGraph,
                        topNavBar = topNavBar
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    navigationGraph: NavigationGraph,
    topNavBar: TopNavBar
) {
    var isVisible by remember { mutableStateOf(true) }
    val snackbarHostState = LocalSnackbarHostState.current
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        isVisible = false
        snackbarHostState.showSnackbar("Welcome to Soothsayer!")
    }

    Scaffold(
        topBar = {
            topNavBar.Content(navController = navController, modifier = Modifier)
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = isVisible,
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.mork_borg),
                            contentScale = ContentScale.FillBounds
                        )
                ) {
                    // License is required to be displayed upon app startup
                    Text(
                        text = stringResource(R.string.license),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = OldNewspaper,
                        modifier = modifier.align(Alignment.BottomCenter)
                    )
                }
            }

            navigationGraph.Content(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainContentPreview() {
    SoothsayerTheme {
        MainContent(
            navigationGraph = object : NavigationGraph {
                @Composable
                override fun Content(navController: androidx.navigation.NavHostController, modifier: Modifier) {
                    // Preview implementation
                }
            },
            topNavBar = object : TopNavBar {
                @Composable
                override fun Content(navController: androidx.navigation.NavController, modifier: Modifier) {
                    // Preview implementation
                }
            }
        )
    }
}