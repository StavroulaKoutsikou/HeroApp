package com.example.heroapp.data

import com.example.heroapp.model.*
import com.example.heroapp.network.RickAndMortyApiService
import com.example.heroapp.data.RickAndMortyRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class RickAndMortyRepositoryFiltersMockTest {

    private lateinit var mockApi: RickAndMortyApiService
    private lateinit var repo: RickAndMortyRepository

    private fun char(
        id: Int,
        name: String,
        species: String,
        originName: String,
        locationName: String
    ) = ApiCharacter(
        id = id,
        name = name,
        status = "",
        species = species,
        type = "",
        gender = "",
        image = "",
        origin = ApiLocationRef(originName, ""),
        location = ApiLocationRef(locationName, ""),
        episode = emptyList()
    )

    @BeforeEach
    fun setUp() {
        mockApi = mock()
        repo = RickAndMortyRepository(mockApi)
    }

    @Test
    fun `given mixed characters, when getRicksAndMortys, then return only Rick and Morty`() = runTest {
        val rick = char(1, "Rick Sanchez", "Human", "Earth C-137", "Citadel")
        val morty = char(2, "Morty Smith", "Human", "Earth", "Earth")
        val summer = char(3, "Summer Smith", "Human", "Earth", "Earth")
        val birdperson = char(4, "Birdperson", "Alien", "Bird World", "Bird World")

        val testList = listOf(rick, morty, summer, birdperson)

        val fakeResponse = CharacterResponse(
            info = ApiInfo(
                count = testList.size,
                pages = 1,
                next = null,
                prev = null
            ),
            results = testList
        )

        whenever(mockApi.getAllCharacters(1)).thenReturn(fakeResponse)
        val result = repo.getRicksAndMortys()
        assertEquals(listOf(rick, morty), result)
        verify(mockApi).getAllCharacters(1)
    }

    @Test
    fun `given mixed origins, when getHumansFromEarth, then return only humans from Earth`() = runTest{
        val human1 = char(1, "Rick Sanchez", "Human", "Earth", "Earth")
        val human2 = char(2, "Jerry Smith", "Human", "Earth", "Earth")
        val humanNotEarth = char(3, "Human Guy", "Human", "Mars", "Station")
        val alienEarth = char(4, "Gromflomite", "Alien", "Earth Colony", "Earth Colony")

        val testList = listOf(human1, human2, humanNotEarth, alienEarth)

        val fakeResponse = CharacterResponse(
            info = ApiInfo(
                count = testList.size,
                pages = 1,
                next = null,
                prev = null
            ),
            results = testList
        )

        whenever(mockApi.getAllCharacters(1)).thenReturn(fakeResponse)
        val result = repo.getHumansFromEarth()

        assertEquals(listOf(human1, human2),result)
        verify(mockApi).getAllCharacters(1)
    }

    @Test
    fun `given mixed species, when getAliensAndCreatures, then return only nonhumans`() = runTest {
        val human = char(1, "Rick Sanchez", "Human", "Earth", "Earth")
        val alien = char(2, "Gromflomite", "Alien", "Gromflom Prime", "Gromflom Prime")
        val robot = char(3, "Gearhead", "Robot", "Gear World", "Gear World")

        val testList = listOf(human, alien, robot)

        val fakeResponse = CharacterResponse(
            info = ApiInfo(
                count = testList.size,
                pages = 1,
                next = null,
                prev = null
            ),
            results = testList
        )

        whenever(mockApi.getAllCharacters(1)).thenReturn(fakeResponse)
        val result = repo.getAliensAndCreatures()

        assertEquals(listOf(alien,robot),result)
        verify(mockApi).getAllCharacters(1)

    }
}
