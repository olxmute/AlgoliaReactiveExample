package com.algolia.example.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "algolia")
data class AlgoliaConfigurationProperties(
    val indexName: String,
    val secrets: AlgoliaSecretsConfigurationProperties
)

data class AlgoliaSecretsConfigurationProperties(
    val applicationId: String,
    val apiKey: String
)