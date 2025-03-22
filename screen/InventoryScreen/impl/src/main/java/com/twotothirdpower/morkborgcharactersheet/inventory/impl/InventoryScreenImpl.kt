package com.twotothirdpower.morkborgcharactersheet.inventory.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import javax.inject.Inject

class InventoryScreenImpl @Inject constructor() : InventoryScreen {
    @Composable
    override fun Content(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Inventory Screen")
        }
    }
} 