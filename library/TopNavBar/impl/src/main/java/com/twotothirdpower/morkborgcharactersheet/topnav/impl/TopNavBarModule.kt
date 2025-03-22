package com.twotothirdpower.morkborgcharactersheet.topnav.impl

import com.twotothirdpower.morkborgcharactersheet.topnav.TopNavBar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopNavBarModule {
    @Provides
    @Singleton
    fun provideTopNavBar(): TopNavBar = TopNavBarImpl()
} 