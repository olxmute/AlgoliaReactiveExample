package com.algolia.example.persistence

import com.algolia.example.dto.AlgoliaProductItem
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
data class ProductItem(
    var algoliaProductItem: AlgoliaProductItem,
) {
    @Id
    var id: String = algoliaProductItem.objectID.raw
}
