package com.twotothirdpower.morkborgcharactersheet.domain.usecases

import com.twotothirdpower.morkborgcharactersheet.domain.StatType

interface GetCurrentCharacterStatUseCase {
    suspend operator fun invoke(statType: StatType): Int
} 