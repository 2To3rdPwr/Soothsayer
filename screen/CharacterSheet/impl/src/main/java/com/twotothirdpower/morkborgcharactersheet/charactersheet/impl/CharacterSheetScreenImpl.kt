package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import javax.inject.Inject
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import androidx.hilt.navigation.compose.hiltViewModel

class CharacterSheetScreenImpl @Inject constructor() : CharacterSheetScreen {
    @Composable
    override fun Content(
        modifier: Modifier,
        onCharacterNameChanged: (String?) -> Unit
    ) {
        val viewModel: CharacterSheetViewModel = hiltViewModel()
        val characterName by viewModel.characterName.collectAsState()
        
        // Call the callback whenever the character name changes
        LaunchedEffect(characterName) {
            onCharacterNameChanged(characterName)
        }
        
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = characterName ?: "No character found")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSheetScreenLightPreview() {
    SoothsayerTheme {
        CharacterSheetScreenImpl().Content(
            modifier = Modifier,
            onCharacterNameChanged = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterSheetScreenDarkPreview() {
    SoothsayerTheme {
        CharacterSheetScreenImpl().Content(
            modifier = Modifier,
            onCharacterNameChanged = {}
        )
    }
} 