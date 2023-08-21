package com.danilovfa.organizer.tasks.domain.repository

import com.danilovfa.organizer.tasks.domain.model.Banner

interface BannerRepository {
    suspend fun getBanners(): List<Banner>
    suspend fun updateBanners() : Result<Unit>
}