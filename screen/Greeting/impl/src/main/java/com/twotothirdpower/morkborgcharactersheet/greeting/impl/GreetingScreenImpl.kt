package com.twotothirdpower.morkborgcharactersheet.greeting.impl

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.OldNewspaper
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.greeting.GreetingScreen
import kotlinx.coroutines.delay
import javax.inject.Inject

class GreetingScreenImpl @Inject constructor() : GreetingScreen {
    @Composable
    override fun Content(
        modifier: Modifier,
        onGreetingComplete: () -> Unit
    ) {
        // Use rememberUpdatedState to ensure `onGreetingComplete` is called changes after the LaunchedEffect starts
        val onComplete by rememberUpdatedState(onGreetingComplete)
        LaunchedEffect(Unit) {
            delay(3000L)
            onComplete()
        }

        Box(
            modifier = modifier
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
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingScreenPreview() {
    SoothsayerTheme {
        GreetingScreenImpl().Content(
            modifier = Modifier.fillMaxSize(),
            onGreetingComplete = {}
        )
    }
} 