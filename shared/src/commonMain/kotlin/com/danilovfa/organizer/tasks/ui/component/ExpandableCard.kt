package com.danilovfa.organizer.tasks.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.Redeem
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danilovfa.organizer.tasks.ui.model.BannerState
import com.danilovfa.organizer.resources.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ExpandableBannerCard(
    banner: BannerState,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "expandedIndicatorRotation"
    )

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                expanded = !expanded
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BannerIcon(
                    banner = banner
                )

                Spacer(Modifier.width(16.dp))

                Text(
                    text = when (banner) {
                        is BannerState.DiscountBannerState -> stringResource(MR.strings.discount)
                        is BannerState.ProductBannerState -> banner.productName
                    },
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(0.5f)
                        .rotate(rotationState)
                )
            }

            AnimatedVisibility(visible = expanded) {
                BannerDescription(banner)
            }
        }
    }
}

@Composable
private fun BannerIcon(
    banner: BannerState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = CircleShape
            )
    ) {
        val imageVector = when (banner) {
            is BannerState.DiscountBannerState -> Icons.Rounded.Sell
            is BannerState.ProductBannerState -> Icons.Rounded.Redeem
        }

        val contentDescription = when (banner) {
            is BannerState.DiscountBannerState -> stringResource(MR.strings.discount)
            is BannerState.ProductBannerState -> stringResource(MR.strings.product)
        }

        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.align(Alignment.Center),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun BannerDescription(banner: BannerState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = when (banner) {
                is BannerState.DiscountBannerState -> stringResource(
                    MR.strings.banner_discount_text,
                    banner.companyName,
                    banner.discountPercentage.toString()
                )

                is BannerState.ProductBannerState -> stringResource(
                    MR.strings.banner_product_title,
                    banner.productName,
                    banner.companyName
                )
            },
            style = MaterialTheme.typography.bodyLarge
        )

        if (banner is BannerState.ProductBannerState) {
            Text(
                text = stringResource(MR.strings.banner_product_subtext, banner.date),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.alpha(0.5f)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = stringResource(MR.strings.banner_product_text)
            )
        }
    }

}