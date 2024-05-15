package org.example.entity

import java.util.UUID

data class Book(val id: UUID = UUID.randomUUID(), val title: String, val author: String, val theme: String, val published: Int)