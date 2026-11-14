package com.example.heroapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiLocationRef(
    val name: String,
    val url: String
)