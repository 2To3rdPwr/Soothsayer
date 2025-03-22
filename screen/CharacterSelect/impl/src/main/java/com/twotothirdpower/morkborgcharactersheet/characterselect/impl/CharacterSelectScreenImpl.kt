package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import javax.inject.Inject

class CharacterSelectScreenImpl @Inject constructor() : CharacterSelectScreen {
    @Composable
    override fun Content(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Character Select Screen")
        }
    }
} 