package com.twotothirdpower.morkborgcharactersheet.domain.impl.DI

import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.DeleteCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GenerateRandomCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GetCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GetCharactersListUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.ImproveCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.SaveCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GenerateRandomCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.ImproveCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.SaveCharacterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    @Singleton
    fun bindGetCharactersListUseCase(
        impl: GetCharactersListUseCaseImpl
    ): GetCharactersListUseCase

    @Binds
    @Singleton
    fun bindSaveCharacterUseCase(
        impl: SaveCharacterUseCaseImpl
    ): SaveCharacterUseCase

    @Binds
    @Singleton
    fun bindDeleteCharacterUseCase(
        impl: DeleteCharacterUseCaseImpl
    ): DeleteCharacterUseCase

    @Binds
    @Singleton
    fun bindGenerateRandomCharacterUseCase(
        impl: GenerateRandomCharacterUseCaseImpl
    ): GenerateRandomCharacterUseCase

    @Binds
    @Singleton
    fun bindImproveCharacterUseCase(
        impl: ImproveCharacterUseCaseImpl
    ): ImproveCharacterUseCase
    
    @Binds
    @Singleton
    fun bindGetCharacterUseCase(
        impl: GetCharacterUseCaseImpl
    ): GetCharacterUseCase
} 