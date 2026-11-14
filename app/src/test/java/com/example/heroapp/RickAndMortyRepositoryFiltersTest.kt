package com.example.heroapp.data

import com.example.heroapp.model.ApiCharacter
import com.example.heroapp.model.ApiInfo
import com.example.heroapp.model.ApiLocationRef
import com.example.heroapp.model.CharacterResponse
import com.example.heroapp.network.RickAndMortyApiService
import com.example.heroapp.data.RickAndMortyRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private class FakeRickAndMortyApiService(
    var characters: List<ApiCharacter>
) : RickAndMortyApiService {

    override suspend fun getAllCharacters(page: Int): CharacterResponse {
        return CharacterResponse(
            info = ApiInfo(
                count = characters.size,
                pages = 1,
                next = null,
                prev = null
            ),
            results = characters
        )
    }

    override suspend fun getCharacter(id: Int): ApiCharacter {
        throw UnsupportedOperationException("Not used in these tests")
    }

    override suspend fun searchCharacters(name: String, page: Int): CharacterResponse {
        throw UnsupportedOperationException("Not used in these tests")
    }
}

class RickAndMortyRepositoryFiltersTest {

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

    private lateinit var fakeApi: FakeRickAndMortyApiService
    private lateinit var repo: RickAndMortyRepository

    @BeforeEach
    fun setUp() {
        fakeApi = FakeRickAndMortyApiService(emptyList())
        repo = RickAndMortyRepository(fakeApi)
    }

    @Test
    fun `given mixed characters, when getRicksAndMortys, then return only Rick and Morty`() = runTest {
        val rick = char(1, "Rick Sanchez", "Human", "Earth C-137", "Citadel")
        val morty = char(2, "Morty Smith", "Human", "Earth", "Earth")
        val summer = char(3, "Summer Smith", "Human", "Earth", "Earth")
        val birdperson = char(4, "Birdperson", "Alien", "Bird World", "Bird World")

        fakeApi.characters = listOf(rick, morty, summer, birdperson)

        val result = repo.getRicksAndMortys()

        assertEquals(listOf(rick, morty), result)
    }

    @Test
    fun `given mixed origins, when getHumansFromEarth, then return only humans from Earth`() = runTest {
        val human1 = char(1, "Rick Sanchez", "Human", "Earth", "Earth")
        val human2 = char(2, "Jerry Smith", "Human", "Earth", "Earth")
        val humanNotEarth = char(3, "Human Guy", "Human", "Mars", "Station")
        val alienEarth = char(4, "Gromflomite", "Alien", "Earth Colony", "Earth Colony")

        fakeApi.characters = listOf(human1, human2, humanNotEarth, alienEarth)

        val result = repo.getHumansFromEarth()

        assertEquals(listOf(human1, human2), result)
    }

    @Test
    fun `given mixed species, when getAliensAndCreatures, then return only nonhuman characters`() = runTest {
        val human = char(1, "Rick Sanchez", "Human", "Earth", "Earth")
        val alien = char(2, "Gromflomite", "Alien", "Gromflom Prime", "Gromflom Prime")
        val robot = char(3, "Gearhead", "Robot", "Gear World", "Gear World")

        fakeApi.characters = listOf(human, alien, robot)

        val result = repo.getAliensAndCreatures()

        assertEquals(listOf(alien, robot), result)
    }
}
