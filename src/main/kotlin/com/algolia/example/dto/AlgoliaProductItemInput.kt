package com.algolia.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlgoliaProductItemInput(
    val name: String,
    val description: String,
)
