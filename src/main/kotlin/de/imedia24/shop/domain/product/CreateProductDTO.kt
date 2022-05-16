package de.imedia24.shop.domain.product

import java.math.BigDecimal
import java.time.ZonedDateTime

data class CreateProductDTO(
    val sku: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int,
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    val updatedAt: ZonedDateTime = ZonedDateTime.now()
)