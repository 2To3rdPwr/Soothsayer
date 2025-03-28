package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import android.content.Context
import androidx.room.Room
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDataModule {
    
    @Provides
    @Singleton
    fun provideCharacterDatabase(
        @ApplicationContext context: Context
    ): CharacterDatabase {
        return Room.databaseBuilder(
            context,
            CharacterDatabase::class.java,
            "character_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(dao: CharacterDao): CharacterRepository {
        return CharacterRepositoryImpl(dao)
    }
} 