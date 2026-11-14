package com.example.heroapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
