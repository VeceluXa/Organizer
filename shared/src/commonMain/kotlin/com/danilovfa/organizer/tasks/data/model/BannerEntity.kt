package com.danilovfa.organizer.tasks.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class BannerEntity() {
    @Serializable
    data class DiscountBannerEntity(
        val companyName: String,
        val discountPercentage: Int
    ) : BannerEntity()

    @Serializable
    data class ProductBannerEntity(
        val productName: String,
        val companyName: String,
        val date: String
    ) : BannerEntity()
}
