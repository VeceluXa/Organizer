package com.danilovfa.organizer.tasks.ui.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class BannerState {
    @Immutable
    data class DiscountBannerState(
        val companyName: String,
        val discountPercentage: Int
    ) : BannerState()

    @Immutable
    data class ProductBannerState(
        val productName: String,
        val companyName: String,
        val date: String
    ) : BannerState()
}
