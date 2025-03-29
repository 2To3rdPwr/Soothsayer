package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import javax.inject.Inject
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.R

private val dummyCharacters = listOf(
    CharacterData(
        characterId = 1,
        lastChanged = System.currentTimeMillis(),
        characterName = "Betsy the Brave",
        characterDescription = "A fearless warrior who lost her left eye in a tragic juggling accident. Now she seeks redemption through violence and mayhem.",
        currentHp = 6,
        maxHp = 8,
        currentOmens = 2,
        currentPowers = 1,
        strength = 3,
        agility = -1,
        presence = 2,
        toughness = 0
    ),
    CharacterData(
        characterId = 2,
        lastChanged = System.currentTimeMillis() - 86400000, // 1 day ago
        characterName = "Grim the Unsanitary",
        characterDescription = "Former plague doctor turned doomsayer. Carries a collection of suspicious herbs and definitely cursed trinkets.",
        currentHp = 4,
        maxHp = 4,
        currentOmens = 0,
        currentPowers = 3,
        strength = -2,
        agility = 1,
        presence = 3,
        toughness = -1
    ),
    CharacterData(
        characterId = 3,
        lastChanged = System.currentTimeMillis() - 172800000, // 2 days ago
        characterName = "Krax the Uncertain",
        characterDescription = "A retired accountant who accidentally made a pact with an elder god while doing taxes.",
        currentHp = 2,
        maxHp = 6,
        currentOmens = 1,
        currentPowers = 2,
        strength = 0,
        agility = 0,
        presence = -2,
        toughness = 1
    )
)

class CharacterSelectScreenImpl @Inject constructor() : CharacterSelectScreen {
    @Composable
    override fun Content(modifier: Modifier) {
        var expandedCharacterId by remember { mutableStateOf<Int?>(null) }
        
        CharacterSelectScreenImpl(
            modifier = modifier,
            characters = dummyCharacters,
            expandedCharacterId = expandedCharacterId,
            onCharacterExpand = { expandedCharacterId = it },
            onCharacterCollapse = { expandedCharacterId = null },
            onCharacterDelete = { /* TODO */ },
            onCharacterOpen = { /* TODO */ },
            onCreateNew = { /* TODO */ }
        )
    }
}

@Composable
private fun CharacterSelectScreenImpl(
    modifier: Modifier = Modifier,
    characters: List<CharacterData> = emptyList(),
    expandedCharacterId: Int? = null,
    onCharacterExpand: (Int) -> Unit = {},
    onCharacterCollapse: () -> Unit = {},
    onCharacterDelete: (CharacterData) -> Unit = {},
    onCharacterOpen: (CharacterData) -> Unit = {},
    onCreateNew: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (characters.isEmpty()) {
                item {
                    EmptyState()
                }
            } else {
                items(characters, key = { it.characterId }) { character ->
                    CharacterListItem(
                        character = character,
                        isExpanded = character.characterId == expandedCharacterId,
                        onExpand = { onCharacterExpand(character.characterId) },
                        onCollapse = onCharacterCollapse,
                        onDelete = { onCharacterDelete(character) },
                        onOpen = { onCharacterOpen(character) }
                    )
                    HorizontalDivider()
                }
            }
        }
        
        Button(
            onClick = onCreateNew,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add New")
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Noone's here yet. Create a new character!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: CharacterData,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onDelete: () -> Unit,
    onOpen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { if (isExpanded) onCollapse() else onExpand() }
    ) {
        Surface(
            shadowElevation = 16.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = character.characterName,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onOpen) {
                    Icon(
                        painter = painterResource(id = R.drawable.up_arrow),
                        contentDescription = "Open ${character.characterName}"
                    )
                }
            }
        }

        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (character.characterDescription.isNotBlank()) {
                    Text(
                        text = character.characterDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildString {
                            append("STR: ${character.strength} ")
                            append("AGI: ${character.agility} ")
                            append("PRES: ${character.presence} ")
                            append("TGH: ${character.toughness}")
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Delete ${character.characterName}"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterSelectPreviewLight() {
    SoothsayerTheme(darkTheme = false) {
        Surface {
            CharacterSelectScreenImpl(
                characters = dummyCharacters,
                expandedCharacterId = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterSelectPreviewDark() {
    SoothsayerTheme(darkTheme = true) {
        Surface {
            CharacterSelectScreenImpl(
                characters = dummyCharacters,
                expandedCharacterId = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterSelectEmptyPreview() {
    SoothsayerTheme {
        Surface {
            CharacterSelectScreenImpl(
                characters = emptyList()
            )
        }
    }
} 