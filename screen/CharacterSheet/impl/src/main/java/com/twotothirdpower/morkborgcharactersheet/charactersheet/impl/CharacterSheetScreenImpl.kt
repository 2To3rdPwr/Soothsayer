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
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoller
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollerInput
import com.twotothirdpower.morkborgcharactersheet.dice.DiceState
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoll
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class CharacterSheetScreenImpl @Inject constructor(
    private val diceRoller: DiceRoller,
    private val diceRollerInput: DiceRollerInput
) : CharacterSheetScreen {
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
            onEditCharacter = { character?.characterId?.let { onEditCharacter(it) } },
            diceRoller = diceRoller,
            diceRollerInput = diceRollerInput
        )
    }
}

@Composable
fun CharacterSheetContent(
    modifier: Modifier = Modifier,
    characterName: String?,
    onEditCharacter: () -> Unit,
    diceRoller: DiceRoller,
    diceRollerInput: DiceRollerInput
) {
    var diceState by remember { mutableStateOf(DiceState()) }
    var rollResult by remember { mutableStateOf<Int?>(null) }
    val coroutineScope = rememberCoroutineScope()
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
        Spacer(modifier = Modifier.weight(1f))
        // Dice Roller Input, Roll Button, and Result
        diceRollerInput.Content(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 20.dp),
            state = diceState,
            showStatModifier = true,
            onDiceRollUpdated = { diceRoll ->
                diceState = diceRoll.toDiceState()
            },
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    rollResult = diceRoller.roll(diceState.toDiceRoll())
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp)
        ) {
            Text("Roll")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = rollResult?.toString() ?: "",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSheetContentPreview() {
    // Fake DiceRoller that always returns 7
    val fakeDiceRoller = object : DiceRoller {
        override suspend fun roll(diceRoll: DiceRoll): Int = 7
    }
    // Fake DiceRollerInput that just shows a placeholder UI
    val fakeDiceRollerInput = object : DiceRollerInput {
        @Composable
        override fun Content(
            modifier: Modifier,
            state: DiceState,
            showStatModifier: Boolean,
            onDiceRollUpdated: (DiceRoll) -> Unit,
            fontSize: TextUnit
        ) {
            Text("DiceRollerInput Preview", modifier = modifier)
        }
    }
    SoothsayerTheme {
        CharacterSheetContent(
            modifier = Modifier,
            characterName = "Buster the Forsaken",
            onEditCharacter = {},
            diceRoller = fakeDiceRoller,
            diceRollerInput = fakeDiceRollerInput
        )
    }
}
