package com.example.heroapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val origin: ApiLocationRef,
    val location: ApiLocationRef,
    val episode: List<String>
)
