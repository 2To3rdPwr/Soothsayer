package com.twotothirdpower.morkborgcharactersheet.managecharacter.impl

import com.twotothirdpower.morkborgcharactersheet.managecharacter.ManageCharacterScreen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ManageCharacterModule {
    @Binds
    abstract fun bindManageCharacterScreen(
        manageCharacterScreenImpl: ManageCharacterScreenImpl
    ): ManageCharacterScreen
} 