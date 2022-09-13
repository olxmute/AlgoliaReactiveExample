package com.algolia.example.resolver

import com.algolia.example.dto.AlgoliaProductItem
import com.algolia.example.dto.AlgoliaProductItemInput
import com.algolia.example.persistence.ProductItem
import com.algolia.example.persistence.ProductItemsRepository
import com.algolia.search.client.Index
import com.algolia.search.helper.deserialize
import com.algolia.search.model.ObjectID
import com.algolia.search.model.search.Query
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@DgsComponent
class ItemsResolver(
    private val mainIndex: Index,
    private val productItemsRepository: ProductItemsRepository
) {

    @DgsQuery
    suspend fun findById(@InputArgument id: String): AlgoliaProductItem? {
        val productItem = productItemsRepository.findById(id)

        if (productItem == null) {
            val algoliaProductItem = mainIndex.getObject(AlgoliaProductItem.serializer(), ObjectID(id))
            return productItemsRepository.save(ProductItem(algoliaProductItem)).algoliaProductItem
        }

        return productItem.algoliaProductItem
    }

    @DgsQuery
    suspend fun searchItems(@InputArgument query: String): Flow<AlgoliaProductItem> {
        val productItems = mainIndex.search(Query(query))
            .hits
            .deserialize(AlgoliaProductItem.serializer())
            .asFlow()

        return productItemsRepository
            .saveAll(productItems.map { ProductItem(it) })
            .map { it.algoliaProductItem }
    }

    @DgsMutation
    suspend fun updateItem(
        @InputArgument id: String,
        @InputArgument item: AlgoliaProductItemInput
    ): AlgoliaProductItem? {
        val algoliaProductItem = AlgoliaProductItem(
            name = item.name,
            description = item.description,
            objectID = ObjectID(id)
        )

        productItemsRepository.save(ProductItem(algoliaProductItem))
        mainIndex.saveObject(AlgoliaProductItem.serializer(), algoliaProductItem)

        return findById(id)
    }

    @DgsMutation
    suspend fun removeItem(@InputArgument id: String): String {
        productItemsRepository.deleteById(id)
        mainIndex.deleteObject(ObjectID(id))

        return id
    }

}