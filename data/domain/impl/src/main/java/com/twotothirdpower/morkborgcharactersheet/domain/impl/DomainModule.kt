package com.twotothirdpower.morkborgcharactersheet.domain.impl

import com.twotothirdpower.morkborgcharactersheet.domain.impl.usecases.GetCharactersListUseCaseImpl
import com.twotothirdpower.morkborgcharactersheet.domain.usecases.GetCharactersListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetCharactersListUseCase(impl: GetCharactersListUseCaseImpl): GetCharactersListUseCase
} 