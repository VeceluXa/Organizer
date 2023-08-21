package com.danilovfa.organizer.tasks.ui.mapper

import com.danilovfa.organizer.tasks.domain.model.Banner
import com.danilovfa.organizer.tasks.ui.model.BannerState

fun Banner.toState(): BannerState {
    return when(this) {
        is Banner.DiscountBanner -> BannerState.DiscountBannerState(
            companyName = companyName,
            discountPercentage = discountPercentage
        )
        is Banner.ProductBanner -> BannerState.ProductBannerState(
            productName = productName,
            companyName = companyName,
            date = date
        )
    }
}