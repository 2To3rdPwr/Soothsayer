package com.twotothirdpower.morkborgcharactersheet

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.OldNewspaper
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold.Companion.LocalSnackbarHostState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackbarScaffold: SnackbarScaffold

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoothsayerTheme {
                snackbarScaffold.Content { modifier ->
                    Greeting(modifier = modifier)
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        isVisible = false
        snackbarHostState.showSnackbar("Welcome to Soothsayer!")
    }

    Box(modifier = modifier.fillMaxSize()) {
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
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    SoothsayerTheme {
        Greeting()
    }
}