package com.algolia.example.resolver

import com.algolia.example.dto.AlgoliaProductItem
import com.algolia.example.dto.AlgoliaProductItemInput
import com.algolia.search.client.Index
import com.algolia.search.helper.deserialize
import com.algolia.search.model.ObjectID
import com.algolia.search.model.search.Query
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ItemsResolver(
    private val mainIndex: Index
) {

    @DgsQuery
    suspend fun findById(@InputArgument id: String): AlgoliaProductItem? {
        return mainIndex.getObject(AlgoliaProductItem.serializer(), ObjectID(id))
    }

    @DgsQuery
    suspend fun searchItems(@InputArgument query: String): List<AlgoliaProductItem> {
        return mainIndex.search(Query(query))
            .hits
            .deserialize(AlgoliaProductItem.serializer())
    }

    @DgsMutation
    suspend fun updateItem(
        @InputArgument id: String,
        @InputArgument item: AlgoliaProductItemInput
    ): AlgoliaProductItem? {
        mainIndex.saveObject(
            AlgoliaProductItem.serializer(),
            AlgoliaProductItem(
                name = item.name,
                description = item.description,
                objectID = ObjectID(id)
            )
        )

        return findById(id)
    }

    @DgsMutation
    suspend fun removeItem(@InputArgument id: String): String {
        mainIndex.deleteObject(ObjectID(id))

        return id
    }

}