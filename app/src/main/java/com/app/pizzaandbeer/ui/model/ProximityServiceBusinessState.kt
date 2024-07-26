package com.app.pizzaandbeer.ui.model

/**
 * UI Model Representation of Business Information
 */
data class ProximityServiceBusinessState(
    val id: String,
    val name: String,
    val imageUrl: String? = null,
    val reviewCountAsString: String? = null,
    val categories: List<String>? = null,
    val ratingAsString: String? = null,
    val transactions: List<String>? = null,
    val price: String? = null,
    val displayAddress: List<String>? = null,
    val phone: String? = null,
    val displayPhone: String? = null,
) : ProximityServicePagingState
