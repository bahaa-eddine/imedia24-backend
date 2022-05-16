package de.imedia24.shop.db.dto

import java.math.BigDecimal

data class UpdateProductDTO(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
)