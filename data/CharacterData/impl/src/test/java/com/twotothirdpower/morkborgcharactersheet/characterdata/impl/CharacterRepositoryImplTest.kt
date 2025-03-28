package com.twotothirdpower.morkborgcharactersheet.characterdata.impl

import com.twotothirdpower.morkborgcharactersheet.characterdata.CharacterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

@RunWith(JUnit4::class)
class CharacterRepositoryImplTest {
    private lateinit var characterDao: CharacterDao
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setup() {
        characterDao = mockk()
        repository = CharacterRepositoryImpl(characterDao)
    }

    @Test
    fun `getAllCharacters returns flow from dao`() = runTest {
        val testFlow: Flow<List<CharacterData>> = flowOf(listOf(createTestCharacter()))
        coEvery { characterDao.getAllCharacters() } returns testFlow

        val result = repository.getAllCharacters()

        assert(result == testFlow)
        coVerify { characterDao.getAllCharacters() }
    }

    @Test
    fun `getCharacterById returns character flow from dao`() = runTest {
        val testCharacter = createTestCharacter()
        val testFlow: Flow<CharacterData> = flowOf(testCharacter)
        coEvery { characterDao.getCharacterById(1) } returns testFlow

        val result = repository.getCharacterById(1)

        assert(result == testFlow)
        coVerify { characterDao.getCharacterById(1) }
    }

    @Test
    fun `insertCharacter calls dao insert`() = runTest {
        val testCharacter = createTestCharacter()
        coEvery { characterDao.insertCharacter(testCharacter) } just runs

        repository.insertCharacter(testCharacter)

        coVerify { characterDao.insertCharacter(testCharacter) }
    }

    @Test
    fun `updateCharacter calls dao update`() = runTest {
        val testCharacter = createTestCharacter()
        coEvery { characterDao.updateCharacter(testCharacter) } just runs

        repository.updateCharacter(testCharacter)

        coVerify { characterDao.updateCharacter(testCharacter) }
    }

    @Test
    fun `deleteCharacter calls dao delete`() = runTest {
        val testCharacter = createTestCharacter()
        coEvery { characterDao.deleteCharacter(testCharacter) } just runs

        repository.deleteCharacter(testCharacter)

        coVerify { characterDao.deleteCharacter(testCharacter) }
    }

    private fun createTestCharacter() = CharacterData(
        characterId = 1,
        lastChanged = System.currentTimeMillis(),
        characterName = "Test Character",
        characterDescription = "Test Description",
        currentHp = 8,
        maxHp = 8,
        currentOmens = 2,
        currentPowers = 5,
        strength = 3,
        agility = 2,
        presence = -1,
        toughness = -2
    )
} 