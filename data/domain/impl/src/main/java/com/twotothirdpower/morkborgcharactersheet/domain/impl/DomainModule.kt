package com.twotothirdpower.morkborgcharactersheet.domain.impl

import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.AddCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.DeleteCharacterUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GetCharactersListUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.AddCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.DeleteCharacterUseCase
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
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
    fun bindGetCharactersListUseCase(impl: GetCharactersListUseCaseImpl): GetCharactersListUseCase

    @Binds
    @Singleton
    fun bindAddCharacterUseCase(impl: AddCharacterUseCaseImpl): AddCharacterUseCase

    @Binds
    @Singleton
    fun bindDeleteCharacterUseCase(impl: DeleteCharacterUseCaseImpl): DeleteCharacterUseCase
} 