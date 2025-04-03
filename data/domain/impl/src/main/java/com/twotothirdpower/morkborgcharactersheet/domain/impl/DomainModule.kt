package com.twotothirdpower.morkborgcharactersheet.domain.impl

import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.AddCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.DeleteCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GenerateRandomCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GetCharactersListUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.ImproveCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.AddCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GenerateRandomCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.ImproveCharacterUseCase
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
    fun bindAddCharacterUseCase(
        impl: AddCharacterUseCaseImpl
    ): AddCharacterUseCase

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
} 