package com.twotothirdpower.morkborgcharactersheet.navigation.impl

import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import com.twotothirdpower.morkborgcharactersheet.greeting.GreetingScreen
import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import com.twotothirdpower.morkborgcharactersheet.navigation.NavigationGraph
import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
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
        inventoryScreen: InventoryScreen,
        greetingScreen: GreetingScreen,
        topNavBar: TopNavBar
    ): NavigationGraph = NavigationGraphImpl(
        characterSelectScreen = characterSelectScreen,
        characterSheetScreen = characterSheetScreen,
        inventoryScreen = inventoryScreen,
        greetingScreen = greetingScreen,
        topNavBar = topNavBar
    )
} 