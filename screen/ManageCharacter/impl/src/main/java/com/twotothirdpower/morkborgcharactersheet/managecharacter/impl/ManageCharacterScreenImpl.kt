package com.twotothirdpower.morkborgcharactersheet.managecharacter.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.GraveDigger
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Red
import com.twotothirdpower.morkborgcharactersheet.managecharacter.ManageCharacterScreen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.R as CommonUiR
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import com.twotothirdpower.morkborgcharactersheet.snackbar.SnackbarScaffold.Companion.LocalSnackbarHostState
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.TheDefiler
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.DarkCollege
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.BlackNight
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Odinson
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Zombie
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.DharmaPunk
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.EnchantedLand
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.OldNewspaper
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.CutTheCrap
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.VirgoDisplay
import androidx.compose.foundation.clickable
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.WhiteOnBlack
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor

@ActivityScoped
class ManageCharacterScreenImpl @Inject constructor() : ManageCharacterScreen {
    @Composable
    override fun Content(
        modifier: Modifier,
        characterId: Int?,
        onNavigateToCharacterSheet: () -> Unit
    ) {
        val viewModel: ManageCharacterViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        val snackbarHostState = LocalSnackbarHostState.current

        LaunchedEffect(Unit) {
            characterId?.let { viewModel.loadCharacter(it) }
        }

        LaunchedEffect(Unit) {
            viewModel.saveComplete.collect {
                onNavigateToCharacterSheet()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.validationError.collect { message ->
                snackbarHostState.showSnackbar(message)
            }
        }

        when (uiState) {
            is ManageCharacterUiState.New -> NewState(
                modifier = modifier,
                onResignToFate = {
                    viewModel.generateRandomCharacter()
                    onNavigateToCharacterSheet()
                },
                onDecideDestiny = viewModel::switchToEditState
            )
            is ManageCharacterUiState.Edit -> EditState(
                state = uiState as ManageCharacterUiState.Edit,
                modifier = modifier,
                onNameChange = viewModel::updateName,
                onDescriptionChange = viewModel::updateDescription,
                onHpChange = viewModel::updateHp,
                onStrengthChange = viewModel::updateStrength,
                onAgilityChange = viewModel::updateAgility,
                onPresenceChange = viewModel::updatePresence,
                onToughnessChange = viewModel::updateToughness,
                onSave = viewModel::saveCharacter,
                onImprove = viewModel::improveCharacter
            )
        }
    }
}

@Composable
private fun NewState(
    modifier: Modifier = Modifier,
    onResignToFate: () -> Unit = {},
    onDecideDestiny: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = CommonUiR.drawable.mork_borg),
                contentScale = ContentScale.FillBounds
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onResignToFate,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Resign yourself\nto fate",
                    fontFamily = GraveDigger,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            
            Button(
                onClick = onDecideDestiny,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Decide your own\ndestiny",
                    fontFamily = GraveDigger,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
private fun StatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
            .width(48.dp)
    ) {
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Red,
            backgroundColor = LocalTextSelectionColors.current.backgroundColor
        )

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontFamily = DharmaPunk
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .align(Alignment.Center)
                    .onFocusChanged { isFocused = it.isFocused },
                cursorBrush = SolidColor(Red)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
                .height(2.dp)
                .background(if (isError) MaterialTheme.colorScheme.error else if (isFocused) Red else Color.Black)
        )
    }
}

@Composable
private fun EditState(
    state: ManageCharacterUiState.Edit,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onHpChange: (Int) -> Unit = {},
    onStrengthChange: (Int) -> Unit = {},
    onAgilityChange: (Int) -> Unit = {},
    onPresenceChange: (Int) -> Unit = {},
    onToughnessChange: (Int) -> Unit = {},
    onSave: () -> Unit = {},
    onImprove: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { 
                    Text(
                        "Name",
                        fontFamily = CutTheCrap,
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontFamily = VirgoDisplay
                ),
                isError = state.nameError,
                supportingText = if (state.nameError) {
                    { Text("Name is required") }
                } else null,
                shape = RoundedCornerShape(0.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Red,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = Red,
                    focusedLabelColor = Red,
                    selectionColors = TextSelectionColors(
                        backgroundColor = Red.copy(alpha = 0.2f),
                        handleColor = Red
                    )
                )
            )

            if (state.characterId != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(onClick = onImprove)
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = CommonUiR.drawable.up_arrow),
                        contentDescription = "Improve character",
                        modifier = Modifier.width(32.dp)
                    )
                    Text(
                        "Improve",
                        fontFamily = WhiteOnBlack,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = onDescriptionChange,
            label = { 
                Text(
                    "Description",
                    fontFamily = EnchantedLand,
                    fontSize = 20.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontFamily = OldNewspaper
            ),
            shape = RoundedCornerShape(0.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Red,
                unfocusedBorderColor = Color.Black,
                cursorColor = Red,
                focusedLabelColor = Red,
                selectionColors = TextSelectionColors(
                    backgroundColor = Red.copy(alpha = 0.2f),
                    handleColor = Red
                )
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Left column - right aligned
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.padding(top = 48.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        "Agility",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        fontFamily = DarkCollege
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    StatTextField(
                        value = state.agility.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { onAgilityChange(it) }
                        },
                        isError = state.agilityError
                    )
                }

                Spacer(modifier = Modifier.padding(top = 48.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        "Toughness",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        fontFamily = BlackNight
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    StatTextField(
                        value = state.toughness.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { onToughnessChange(it) }
                        },
                        isError = state.toughnessError
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right column - left aligned
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    StatTextField(
                        value = state.strength.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { onStrengthChange(it) }
                        },
                        isError = state.strengthError
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Strength",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        fontFamily = TheDefiler
                    )
                }

                Spacer(modifier = Modifier.padding(top = 48.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    StatTextField(
                        value = state.presence.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { onPresenceChange(it) }
                        },
                        isError = state.presenceError
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Presence",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        fontFamily = Odinson
                    )
                }

                Spacer(modifier = Modifier.padding(top = 48.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    StatTextField(
                        value = state.hp.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { onHpChange(it) }
                        },
                        isError = state.hpError
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "HP",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        fontFamily = Zombie
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Red,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Save Character",
                fontFamily = GraveDigger,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewStatePreview() {
    NewState(
        modifier = Modifier.fillMaxSize(),
        onResignToFate = {},
        onDecideDestiny = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun EditStatePreview() {
    EditState(
        state = ManageCharacterUiState.Edit(
            characterId = 0,
            name = "Doomed Wanderer",
            description = "A lost soul seeking redemption",
            hp = 10,
            strength = 3,
            agility = 2,
            presence = 4,
            toughness = 1
        ),
        modifier = Modifier.fillMaxSize(),
        onNameChange = {},
        onDescriptionChange = {},
        onHpChange = {},
        onStrengthChange = {},
        onAgilityChange = {},
        onPresenceChange = {},
        onToughnessChange = {},
        onSave = {},
        onImprove = {}
    )
}
