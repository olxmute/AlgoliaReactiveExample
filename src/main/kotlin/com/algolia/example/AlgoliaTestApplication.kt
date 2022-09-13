package com.algolia.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class AlgoliaTestApplication

fun main(args: Array<String>) {
	runApplication<AlgoliaTestApplication>(*args)
}
