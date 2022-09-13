package com.algolia.example.persistence

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductItemsRepository : CoroutineCrudRepository<ProductItem, String>