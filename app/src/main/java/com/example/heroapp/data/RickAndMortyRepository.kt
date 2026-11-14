package com.example.heroapp.data

import com.example.heroapp.model.ApiCharacter
import com.example.heroapp.model.CharacterResponse
import com.example.heroapp.network.RickAndMortyApiService
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApiService
) {

    suspend fun getCharacters(page: Int): CharacterResponse {
        return api.getAllCharacters(page)
    }
    suspend fun getCharacter(id: Int): ApiCharacter {
        return api.getCharacter(id)
    }
    suspend fun searchCharacters(name: String, page: Int = 1): CharacterResponse {
        return api.searchCharacters(name, page)
    }
    private suspend fun fetchCharactersPages(maxPages: Int = 5): List<ApiCharacter> {
        val all = mutableListOf<ApiCharacter>()
        var page = 1

        while (page <= maxPages) {
            val response = getCharacters(page)
            all += response.results

            if (response.info.next == null) break
            page++
        }
        return all
    }
    suspend fun getRicksAndMortys(): List<ApiCharacter> {
        val all = fetchCharactersPages()
        return all.filter { character ->
            character.name.contains("Rick", ignoreCase = true) ||
                    character.name.contains("Morty", ignoreCase = true)
        }
    }
    suspend fun getHumansFromEarth(): List<ApiCharacter> {
        val all = fetchCharactersPages()
        return all.filter { character ->
            character.species.equals("Human", ignoreCase = true) &&
                    (
                            character.origin.name.contains("Earth", ignoreCase = true) ||
                                    character.location.name.contains("Earth", ignoreCase = true)
                            )
        }
    }
    suspend fun getAliensAndCreatures(): List<ApiCharacter> {
        val all = fetchCharactersPages()
        return all.filter { character ->
            !character.species.equals("Human", ignoreCase = true)
        }
    }
}