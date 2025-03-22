package com.twotothirdpower.morkborgcharactersheet.topnav.impl

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import javax.inject.Inject

class TopNavBarImpl @Inject constructor() : TopNavBar {
    @Composable
    override fun Content(
        modifier: Modifier,
        selectedTab: Int,
        onTabSelected: (Int) -> Unit
    ) {
        val items = listOf(
            NavigationItem(
                index = 0,
                icon = R.drawable.ic_character_select,
                label = "Characters"
            ),
            NavigationItem(
                index = 1,
                icon = R.drawable.ic_character_sheet,
                label = "Sheet"
            ),
            NavigationItem(
                index = 2,
                icon = R.drawable.ic_inventory,
                label = "Inventory"
            )
        )

        NavigationBar(modifier = modifier) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.label) },
                    label = { Text(text = item.label) },
                    selected = selectedTab == item.index,
                    onClick = { onTabSelected(item.index) }
                )
            }
        }
    }

    private data class NavigationItem(
        val index: Int,
        val icon: Int,
        val label: String
    )
} 