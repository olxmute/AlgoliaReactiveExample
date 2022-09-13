package com.algolia.example.config

import com.algolia.search.client.ClientSearch
import com.algolia.search.client.Index
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlgoliaClientConfiguration {

    @Bean
    fun clientSearch(properties: AlgoliaConfigurationProperties): ClientSearch {
        return ClientSearch(
            applicationID = ApplicationID(properties.secrets.applicationId),
            apiKey = APIKey(properties.secrets.apiKey)
        )
    }

    @Bean
    fun mainIndex(clientSearch: ClientSearch, properties: AlgoliaConfigurationProperties): Index {
        return clientSearch.initIndex(IndexName(properties.indexName))
    }

}