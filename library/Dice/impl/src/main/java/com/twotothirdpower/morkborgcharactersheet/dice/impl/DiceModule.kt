package com.twotothirdpower.morkborgcharactersheet.dice.impl

import com.twotothirdpower.morkborgcharactersheet.dice.DiceRoller
import com.twotothirdpower.morkborgcharactersheet.dice.DiceRollerInput
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCurrentCharacterStatUseCase
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
    fun provideDiceRoller(
        randomProvider: RandomProvider,
        getCurrentCharacterStatUseCase: GetCurrentCharacterStatUseCase
    ): DiceRoller = DiceRollerImpl(randomProvider, getCurrentCharacterStatUseCase)

    @Provides
    @Singleton
    fun provideDiceRollerInput(): DiceRollerInput = DiceRollerInputImpl()

    @Provides
    @Singleton
    fun provideRandomProvider(): RandomProvider = DefaultRandomProvider()
} 