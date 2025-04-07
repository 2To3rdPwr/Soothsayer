package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
} 