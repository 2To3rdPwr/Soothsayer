package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import javax.inject.Inject
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme

class CharacterSheetScreenImpl @Inject constructor() : CharacterSheetScreen {
    @Composable
    override fun Content(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Character Sheet Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSheetScreenLightPreview() {
    SoothsayerTheme {
        CharacterSheetScreenImpl().Content(
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterSheetScreenDarkPreview() {
    SoothsayerTheme {
        CharacterSheetScreenImpl().Content(
            modifier = Modifier
        )
    }
} 