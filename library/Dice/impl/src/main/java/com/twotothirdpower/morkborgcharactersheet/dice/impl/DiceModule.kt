package com.twotothirdpower.morkborgcharactersheet.dice.impl

import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoller
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollerInput
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiceModule {
    @Provides
    @Singleton
    fun provideDiceRoller(randomProvider: RandomProvider): DiceRoller = DiceRollerImpl(randomProvider)

    @Provides
    @Singleton
    fun provideDiceRollerInput(): DiceRollerInput = DiceRollerInputImpl()
} 