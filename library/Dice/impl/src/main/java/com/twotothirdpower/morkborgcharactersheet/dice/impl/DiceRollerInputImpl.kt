package com.twotothirdpower.morkborgcharactersheet.dice.impl

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.Alignment
import com.twotothirdpower.morkborgcharactersheet.dice.*
import javax.inject.Inject
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.CutTheCrap
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.GraveDigger
import androidx.compose.ui.unit.TextUnit

class DiceRollerInputImpl @Inject constructor() : DiceRollerInput {
    @Composable
    override fun Content(
        modifier: Modifier,
        state: DiceState,
        showStatModifier: Boolean,
        onDiceRollUpdated: (DiceRoll) -> Unit,
        fontSize: TextUnit
    ) {
        var diceValueExpanded by remember { mutableStateOf(false) }
        var statTypeExpanded by remember { mutableStateOf(false) }

        val textMeasurer = rememberTextMeasurer()
        val numberFieldWidth: Dp = 36.dp
        val diceValueTextWidth = textMeasurer.measure("D100", style = LocalTextStyle.current.copy(fontSize = fontSize)).size.width
        val statTypeTextWidth = textMeasurer.measure("Toughness", style = LocalTextStyle.current.copy(fontSize = fontSize)).size.width
        val diceDropdownWidth = (diceValueTextWidth * 0.8).dp
        val statDropdownWidth = (statTypeTextWidth  * 0.6).dp

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Amount
            DiceNumberField(
                value = state.amount.toString(),
                onValueChange = { newValue ->
                    val amount = newValue.toIntOrNull() ?: 0
                    onDiceRollUpdated(
                        state.copy(amount = amount).toDiceRoll()
                    )
                },
                modifier = Modifier
                    .width(numberFieldWidth)
                    .padding(end = 4.dp),
                fontSize = fontSize
            )
            // DiceValue Dropdown (compact style)
            CompactDropdown(
                value = state.diceValue.name,
                options = DiceValue.entries.map { it.name },
                expanded = diceValueExpanded,
                onExpandedChange = { diceValueExpanded = it },
                onOptionSelected = { selected ->
                    val selectedValue = DiceValue.valueOf(selected)
                    onDiceRollUpdated(state.copy(diceValue = selectedValue).toDiceRoll())
                },
                modifier = Modifier
                    .width(diceDropdownWidth),
                fontSize = fontSize
            )
            Text(
                "+",
                fontFamily = CutTheCrap,
                fontSize = fontSize,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
            // Misc Modifier
            DiceNumberField(
                value = state.miscModifier.toString(),
                onValueChange = { newValue ->
                    val miscModifier = newValue.toIntOrNull() ?: 0
                    onDiceRollUpdated(
                        state.copy(miscModifier = miscModifier).toDiceRoll()
                    )
                },
                modifier = Modifier
                    .width(numberFieldWidth)
                    .padding(end = 4.dp),
                fontSize = fontSize
            )
            if (showStatModifier) {
                Text(
                    "+",
                    fontFamily = CutTheCrap,
                    fontSize = fontSize,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
                // StatType Dropdown (compact style)
                CompactDropdown(
                    value = state.statType.name,
                    options = StatType.entries.map { it.name },
                    expanded = statTypeExpanded,
                    onExpandedChange = { statTypeExpanded = it },
                    onOptionSelected = { selected ->
                        val selectedType = StatType.valueOf(selected)
                        onDiceRollUpdated(state.copy(statType = selectedType).toDiceRoll())
                    },
                    modifier = Modifier
                        .width(statDropdownWidth),
                    fontSize = fontSize
                )
            }
        }
    }

    @Composable
    private fun DiceNumberField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        fontSize: TextUnit
    ) {
        var isFocused by remember { mutableStateOf(false) }
        var localValue by remember { mutableStateOf(value) }
        // Sync localValue with parent state if it changes externally
        LaunchedEffect(value) {
            if (!isFocused && value != localValue) localValue = value
        }
        Box(modifier = modifier.height(28.dp)) {
            BasicTextField(
                value = localValue,
                onValueChange = {
                    localValue = it
                    onValueChange(it)
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                    fontFamily = GraveDigger
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        val nowFocused = focusState.isFocused
                        if (isFocused && !nowFocused && localValue.isBlank()) {
                            localValue = "0"
                            onValueChange("0")
                        }
                        isFocused = nowFocused
                    },
                cursorBrush = SolidColor(Color.Red)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp)
                    .height(2.dp)
                    .background(if (isFocused) Color.Red else Color.Black)
            )
        }
    }

    @Composable
    private fun CompactDropdown(
        value: String,
        options: List<String>,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        onOptionSelected: (String) -> Unit,
        modifier: Modifier = Modifier,
        fontSize: TextUnit
    ) {
        var isFocused by remember { mutableStateOf(false) }
        Box(modifier = modifier.height(28.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(Color.Transparent)
                    .onFocusChanged { isFocused = it.isFocused }
                    .clickable { onExpandedChange(true) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Start,
                        fontSize = fontSize,
                        fontFamily = CutTheCrap
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp)
                    .height(2.dp)
                    .background(if (expanded || isFocused) Color.Red else Color.Black)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontFamily = CutTheCrap, fontSize = fontSize) },
                        onClick = {
                            onOptionSelected(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DiceRollerInputPreview() {
        DiceRollerInputImpl().Content(
            modifier = Modifier,
            state = DiceState(100, DiceValue.D100, StatType.TOUGHNESS, 100),
            showStatModifier = true,
            onDiceRollUpdated = {},
            fontSize = 14.sp
        )
    }
} 