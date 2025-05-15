package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import javax.inject.Inject
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.CutTheCrap
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.R as CommonUiR
import androidx.hilt.navigation.compose.hiltViewModel

class CharacterSheetScreenImpl @Inject constructor() : CharacterSheetScreen {
    @Composable
    override fun Content(
        modifier: Modifier,
        onCharacterNameChanged: (String?) -> Unit,
        onEditCharacter: (Int) -> Unit
    ) {
        val viewModel: CharacterSheetViewModel = hiltViewModel()
        val characterName by viewModel.characterName.collectAsState()
        val character by viewModel.character.collectAsState()
        
        // Call the callback whenever the character name changes
        LaunchedEffect(characterName) {
            onCharacterNameChanged(characterName)
        }
        
        CharacterSheetContent(
            modifier = modifier,
            characterName = characterName,
            onEditCharacter = { character?.characterId?.let { onEditCharacter(it) } }
        )
    }
}

@Composable
fun CharacterSheetContent(
    modifier: Modifier = Modifier,
    characterName: String?,
    onEditCharacter: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Character Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = characterName ?: "",
                fontFamily = CutTheCrap,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                maxLines = 2,
                minLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 32.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                softWrap = true
            )
            Icon(
                painter = painterResource(id = CommonUiR.drawable.pencil),
                contentDescription = "Edit character",
                modifier = Modifier
                    .height(42.dp)
                    .clickable { onEditCharacter() }
            )
        }
        // ... (rest of the screen content goes here)
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSheetContentPreview() {
    SoothsayerTheme {
        CharacterSheetContent(
            modifier = Modifier,
            characterName = "Buster the foresaken",
            onEditCharacter = {}
        )
    }
}
