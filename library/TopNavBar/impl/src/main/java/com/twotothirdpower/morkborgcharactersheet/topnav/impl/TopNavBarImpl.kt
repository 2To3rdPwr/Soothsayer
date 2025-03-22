package com.twotothirdpower.morkborgcharactersheet.topnav.impl

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import javax.inject.Inject
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.SoothsayerTheme
import androidx.compose.foundation.layout.fillMaxWidth
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Black
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.CutTheCrap
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Red
import com.twotothirdpower.morkborgcharactersheet.commonuiresources.Yellow

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
                label = "Characters"
            ),
            NavigationItem(
                index = 1,
                label = "Sheet"
            ),
            NavigationItem(
                index = 2,
                label = "Inventory"
            )
        )

        TabRow(
            selectedTabIndex = selectedTab,
            modifier = modifier.fillMaxWidth(),
            containerColor = Yellow,
            indicator = {
                // Empty indicator to remove the bar
            }
        ) {
            items.forEach { item ->
                Tab(
                    selected = selectedTab == item.index,
                    onClick = { onTabSelected(item.index) },
                    text = { 
                        Text(
                            text = item.label,
                            color = if (selectedTab == item.index) Red else Black,
                            fontFamily = CutTheCrap
                        )
                    }
                )
            }
        }
    }

    private data class NavigationItem(
        val index: Int,
        val label: String
    )
}

@Preview(showBackground = true)
@Composable
fun TopNavBarLightPreview() {
    SoothsayerTheme {
        TopNavBarImpl().Content(
            modifier = Modifier,
            selectedTab = 0,
            onTabSelected = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopNavBarDarkPreview() {
    SoothsayerTheme {
        TopNavBarImpl().Content(
            modifier = Modifier,
            selectedTab = 1,
            onTabSelected = {}
        )
    }
} 