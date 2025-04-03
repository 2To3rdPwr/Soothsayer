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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.twotothirdpower.morkborgcharactersheet.domain.models.CharacterListItem
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterSelectScreenImpl @Inject constructor() : CharacterSelectScreen {
    @Composable
    override fun Content(
        modifier: Modifier,
        onNavigateToNewCharacter: () -> Unit,
        onNavigateToEditCharacter: (Int) -> Unit
    ) {
        val viewModel: CharacterSelectViewModel = hiltViewModel()
        val characters by viewModel.characters.collectAsState(initial = emptyList())
        var expandedCharacterId by remember { mutableStateOf<Int?>(null) }

        CharacterSelectScreenImpl(
            modifier = modifier,
            characters = characters,
            expandedCharacterId = expandedCharacterId,
            onCharacterExpand = { expandedCharacterId = it },
            onCharacterCollapse = { expandedCharacterId = null },
            onCharacterDelete = { viewModel.deleteCharacter(it) },
            onCharacterOpen = { onNavigateToEditCharacter(it.characterId) },
            onCreateNew = onNavigateToNewCharacter
        )
    }
}

@Composable
private fun CharacterSelectScreenImpl(
    modifier: Modifier = Modifier,
    characters: List<CharacterListItem> = emptyList(),
    expandedCharacterId: Int? = null,
    onCharacterExpand: (Int) -> Unit = {},
    onCharacterCollapse: () -> Unit = {},
    onCharacterDelete: (CharacterListItem) -> Unit = {},
    onCharacterOpen: (CharacterListItem) -> Unit = {},
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
    character: CharacterListItem,
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
                            append("STR: ${character.str} ")
                            append("AGI: ${character.agi} ")
                            append("PRES: ${character.pres} ")
                            append("TGH: ${character.tgh}")
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
                characters = listOf(
                    CharacterListItem(
                        characterId = 1,
                        characterName = "Betsy the Brave",
                        characterDescription = "A fearless warrior who lost her left eye in a tragic juggling accident.",
                        str = 3,
                        agi = -1,
                        pres = 2,
                        tgh = 0
                    ),
                    CharacterListItem(
                        characterId = 2,
                        characterName = "Grim the Unsanitary",
                        characterDescription = "Former plague doctor turned doomsayer.",
                        str = -2,
                        agi = 1,
                        pres = 3,
                        tgh = -1
                    )
                ),
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
                characters = listOf(
                    CharacterListItem(
                        characterId = 1,
                        characterName = "Betsy the Brave",
                        characterDescription = "A fearless warrior who lost her left eye in a tragic juggling accident.",
                        str = 3,
                        agi = -1,
                        pres = 2,
                        tgh = 0
                    ),
                    CharacterListItem(
                        characterId = 2,
                        characterName = "Grim the Unsanitary",
                        characterDescription = "Former plague doctor turned doomsayer.",
                        str = -2,
                        agi = 1,
                        pres = 3,
                        tgh = -1
                    )
                ),
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