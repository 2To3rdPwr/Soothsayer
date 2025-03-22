package com.twotothirdpower.morkborgcharactersheet.navigation.impl

import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationGraph(
        characterSelectScreen: CharacterSelectScreen,
        characterSheetScreen: CharacterSheetScreen,
        inventoryScreen: InventoryScreen
    ): NavigationGraph = NavigationGraphImpl(
        characterSelectScreen = characterSelectScreen,
        characterSheetScreen = characterSheetScreen,
        inventoryScreen = inventoryScreen
    )
} 