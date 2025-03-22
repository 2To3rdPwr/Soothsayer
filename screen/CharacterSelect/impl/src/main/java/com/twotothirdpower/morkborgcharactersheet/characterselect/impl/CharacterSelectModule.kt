package com.twotothirdpower.morkborgcharactersheet.characterselect.impl

import com.twotothirdpower.morkborgcharactersheet.characterselect.CharacterSelectScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterSelectModule {
    @Provides
    @Singleton
    fun provideCharacterSelectScreen(): CharacterSelectScreen = CharacterSelectScreenImpl()
} 