package com.twotothirdpower.morkborgcharactersheet.greeting.impl

import com.twotothirdpower.morkborgcharactersheet.greeting.GreetingScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GreetingModule {
    @Provides
    @Singleton
    fun provideGreetingScreen(): GreetingScreen = GreetingScreenImpl()
} 