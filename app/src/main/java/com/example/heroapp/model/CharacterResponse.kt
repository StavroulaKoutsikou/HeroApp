package com.example.heroapp.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val info: ApiInfo,
    val results: List<ApiCharacter>
)
