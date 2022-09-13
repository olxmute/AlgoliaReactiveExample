package com.algolia.example.dto

import com.algolia.search.model.ObjectID
import com.algolia.search.model.indexing.Indexable
import kotlinx.serialization.Serializable

@Serializable
data class AlgoliaProductItem(
    val name: String,
    val description: String,
    override val objectID: ObjectID
) : Indexable