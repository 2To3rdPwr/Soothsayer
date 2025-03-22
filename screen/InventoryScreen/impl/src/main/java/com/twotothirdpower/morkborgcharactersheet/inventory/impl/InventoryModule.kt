package com.twotothirdpower.morkborgcharactersheet.inventory.impl

import com.twotothirdpower.morkborgcharactersheet.inventory.InventoryScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InventoryModule {
    @Provides
    @Singleton
    fun provideInventoryScreen(): InventoryScreen = InventoryScreenImpl()
} 