package com.danilovfa.organizer.tasks.data.repository

import com.danilovfa.organizer.core.data.FileManager
import com.danilovfa.organizer.core.utils.UUID
import com.danilovfa.organizer.core.utils.toFormattedString
import com.danilovfa.organizer.tasks.data.exception.WrongBannerTypeException
import com.danilovfa.organizer.tasks.domain.model.Banner
import com.danilovfa.organizer.tasks.domain.repository.BannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import com.danilovfa.organizer.tasks.data.mapper.BannerEntityMapper
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.random.nextInt

class BannerRepositoryImpl(
    private val fileManager: FileManager,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO
) : BannerRepository {

    private val bannerMapper = BannerEntityMapper()

    override suspend fun getBanners(): List<Banner> {
        val fileNames = fileManager.getAllFiles(BANNERS_DIR)
        var bannersBytes = listOf<ByteArray>()

        withContext(ioDispatcher) {
            bannersBytes = fileNames.map { fileName ->
                async { fileManager.readFromFile(BANNERS_DIR, fileName) }
            }.awaitAll()
        }

        return bannersBytes.mapNotNull { bannerBytes ->
            try {
                bannerMapper.fromDomain(bannerBytes)
            } catch (e: WrongBannerTypeException) {
                null
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun updateBanners(): Result<Unit> = runCatching {
        val banners = mutableListOf<Banner>()

        banners.add(generateDiscountBanner())
        repeat(Random.nextInt(1, 5)) {
            banners.add(generateProductBanner())
        }

        val bannersBytes = banners.map { banner ->
            bannerMapper.toDomain(banner)
        }

        val fileNames = List(banners.size) {
            UUID.generate()
        }

        fileManager.deleteAllFiles(BANNERS_DIR)
        bannersBytes.zip(fileNames).forEach { (bannerBytes, fileName) ->
            fileManager.writeToFile(BANNERS_DIR, fileName, bannerBytes)
        }
    }

    private fun generateDiscountBanner(): Banner.DiscountBanner {
        val discountPercentage = Random.nextInt(1, 100)
        val companyName = COMPANIES.random()

        return Banner.DiscountBanner(
            companyName = companyName,
            discountPercentage = discountPercentage
        )
    }

    private fun generateProductBanner(): Banner.ProductBanner {
        val product = PRODUCTS.random()
        val company = COMPANIES.random()

        val startDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val endDate =
            startDate.plus(DatePeriod(months = 1))

        val date = LocalDate.fromEpochDays(Random.nextInt(
            startDate.toEpochDays()..endDate.toEpochDays())
        ).toFormattedString()

        return Banner.ProductBanner(
            productName = product,
            companyName = company,
            date = date
        )
    }

    companion object {
        val COMPANIES = listOf(
            "Google",
            "Amazon",
            "Tesla",
            "Meta",
            "Netflix"
        )
        val PRODUCTS = listOf(
            "Phone",
            "Tablet",
            "TV",
            "Watch",
            "Car"
        )

        const val BANNERS_DIR = "banners"
    }
}