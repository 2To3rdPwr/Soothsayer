package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterSheetModule {
    @Provides
    @Singleton
    fun provideCharacterSheetScreen(): CharacterSheetScreen = CharacterSheetScreenImpl()
} 