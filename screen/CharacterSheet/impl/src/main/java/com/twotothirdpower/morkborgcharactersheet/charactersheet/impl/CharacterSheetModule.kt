package com.twotothirdpower.morkborgcharactersheet.charactersheet.impl

import com.twotothirdpower.morkborgcharactersheet.charactersheet.CharacterSheetScreen
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoller
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollerInput
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
    fun provideCharacterSheetScreen(
        diceRoller: DiceRoller,
        diceRollerInput: DiceRollerInput
    ): CharacterSheetScreen = CharacterSheetScreenImpl(diceRoller, diceRollerInput)
} 