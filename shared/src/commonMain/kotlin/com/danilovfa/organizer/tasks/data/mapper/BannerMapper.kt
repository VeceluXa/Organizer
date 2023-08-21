package com.danilovfa.organizer.tasks.data.mapper

import com.danilovfa.organizer.core.utils.Mapper
import com.danilovfa.organizer.tasks.data.model.BannerEntity
import com.danilovfa.organizer.tasks.domain.model.Banner
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class BannerEntityMapper : Mapper<Banner, ByteArray> {

    private val module = SerializersModule {
        polymorphic(BannerEntity::class) {
            subclass(BannerEntity.ProductBannerEntity::class)
            subclass(BannerEntity.DiscountBannerEntity::class)
        }
    }

    private val jsonFormat = Json { serializersModule = module }

    override fun fromDomain(domain: ByteArray): Banner {
        val fileText = domain.toString(Charsets.UTF_8)
        val bannerEntity = jsonFormat.decodeFromString(BannerEntity.serializer(), fileText)

        return when (bannerEntity) {
            is BannerEntity.DiscountBannerEntity -> Banner.DiscountBanner(
                bannerEntity.companyName,
                bannerEntity.discountPercentage
            )

            is BannerEntity.ProductBannerEntity -> Banner.ProductBanner(
                bannerEntity.productName,
                bannerEntity.companyName,
                bannerEntity.date
            )
        }
    }

    override fun toDomain(entity: Banner): ByteArray {

        val bannerEntity = when (entity) {
            is Banner.DiscountBanner -> BannerEntity.DiscountBannerEntity(
                entity.companyName,
                entity.discountPercentage
            )

            is Banner.ProductBanner -> BannerEntity.ProductBannerEntity(
                entity.productName,
                entity.companyName,
                entity.date
            )
        }

        val jsonEntity = jsonFormat.encodeToString(BannerEntity.serializer(), bannerEntity)
        return jsonEntity.toByteArray(Charsets.UTF_8)
    }

}