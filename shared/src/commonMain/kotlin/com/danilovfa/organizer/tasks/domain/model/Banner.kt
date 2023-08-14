package com.danilovfa.organizer.tasks.domain.model

sealed class Banner {
    data class DiscountBanner(
        val companyName: String,
        val discountPercentage: Int
    ) : Banner()
    data class ProductBanner(
        val productName: String,
        val companyName: String,
        val date: String
    ) : Banner()
}
