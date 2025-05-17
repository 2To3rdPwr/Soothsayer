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

class DiceRollerInputImpl @Inject constructor() : DiceRollerInput {
    @Composable
    override fun Content(
        modifier: Modifier,
        state: DiceState,
        showStatModifier: Boolean,
        onDiceRollUpdated: (DiceRoll) -> Unit
    ) {
        var diceValueExpanded by remember { mutableStateOf(false) }
        var statTypeExpanded by remember { mutableStateOf(false) }

        val textMeasurer = rememberTextMeasurer()
        // Measure widest DiceValue by name
        val widestDiceValue = DiceValue.entries.maxByOrNull { it.name.length }?.name ?: ""
        val diceValueTextWidth = textMeasurer.measure(widestDiceValue, style = LocalTextStyle.current).size.width
        // Measure widest StatType (uppercase, as rendered)
        val widestStatType = StatType.entries.maxByOrNull { it.name.length }?.name ?: ""
        val statTypeTextWidth = textMeasurer.measure(widestStatType, style = LocalTextStyle.current).size.width
        // Add extra padding for dropdown icon and comfort
        val diceDropdownWidth = (diceValueTextWidth).dp
        val statDropdownWidth = (statTypeTextWidth).dp // Increased padding for icon and comfort
        val numberFieldWidth: Dp = 48.dp

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
                    .padding(end = 4.dp)
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
                    .width(diceDropdownWidth)
            )
            Text(" + ", modifier = Modifier.padding(horizontal = 2.dp))
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
                    .padding(end = 4.dp)
            )
            if (showStatModifier) {
                Text(" + ", modifier = Modifier.padding(horizontal = 2.dp))
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
                        .width(statDropdownWidth)
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DiceRollerInputPreview() {
        DiceRollerInputImpl().Content(
            modifier = Modifier,
            state = DiceState(100, DiceValue.D100, StatType.TOUGHNESS, 100),
            showStatModifier = true,
            onDiceRollUpdated = {}
        )
    }

    @Composable
    private fun DiceNumberField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val isFocused by remember { mutableStateOf(false) }
        val fieldHeight = 36.dp
        val underlinePadding = 30.dp
        Box(modifier = modifier.height(fieldHeight)) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                cursorBrush = SolidColor(Color.Red)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = underlinePadding)
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
        modifier: Modifier = Modifier
    ) {
        var isFocused by remember { mutableStateOf(false) }
        val fieldHeight = 36.dp
        val underlinePadding = 30.dp
        Box(modifier = modifier.height(fieldHeight)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(fieldHeight)
                    .background(Color.Transparent)
                    .onFocusChanged { isFocused = it.isFocused }
                    .clickable { onExpandedChange(true) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
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
                    .padding(top = underlinePadding)
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
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
} 