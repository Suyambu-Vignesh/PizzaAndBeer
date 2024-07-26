package com.app.pizzaandbeer.data.model

data class Businesses(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val reviewCount: Int? = null,
    val categories: List<String>? = null,
    val rating: Double? = null,
    val transactions: List<String>? = null,
    val price: String? = null,
    val displayAddress: List<String>? = null,
    val phone: String? = null,
    val displayPhone: String? = null,
)
