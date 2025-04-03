package com.twotothirdpower.morkborgcharactersheet.managecharacter.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.GraveDigger
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Red
import com.twotothirdpower.morkborgcharactersheet.managecharacter.ManageCharacterScreen
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.R as CommonUiR
import androidx.compose.ui.tooling.preview.Preview

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

        // Initialize the correct state based on characterId
        LaunchedEffect(characterId) {
            if (characterId != null) {
                viewModel.loadCharacter(characterId)
            }
        }

        // Handle navigation after successful save
        LaunchedEffect(Unit) {
            viewModel.saveComplete.collect {
                onNavigateToCharacterSheet()
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
                onSave = viewModel::saveCharacter
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onResignToFate,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Resign yourself to fate",
                    fontFamily = GraveDigger
                )
            }
            
            Spacer(modifier = Modifier.padding(8.dp))
            
            Button(
                onClick = onDecideDestiny,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Decide your own destiny",
                    fontFamily = GraveDigger
                )
            }
        }
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
    onSave: () -> Unit = {}
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
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = state.nameError,
            supportingText = if (state.nameError) {
                { Text("Name is required") }
            } else null
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.hp.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let { onHpChange(it) }
            },
            label = { Text("HP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.strength.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let { onStrengthChange(it) }
            },
            label = { Text("Strength") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.strengthError,
            supportingText = if (state.strengthError) {
                { Text("Must be between 0 and 6") }
            } else null
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.agility.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let { onAgilityChange(it) }
            },
            label = { Text("Agility") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.agilityError,
            supportingText = if (state.agilityError) {
                { Text("Must be between 0 and 6") }
            } else null
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.presence.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let { onPresenceChange(it) }
            },
            label = { Text("Presence") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.presenceError,
            supportingText = if (state.presenceError) {
                { Text("Must be between 0 and 6") }
            } else null
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = state.toughness.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let { onToughnessChange(it) }
            },
            label = { Text("Toughness") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = state.toughnessError,
            supportingText = if (state.toughnessError) {
                { Text("Must be between 0 and 6") }
            } else null
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Red,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Save Character",
                fontFamily = GraveDigger
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
        onSave = {}
    )
}
