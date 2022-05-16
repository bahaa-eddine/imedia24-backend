package de.imedia24.shop.db.entity

import de.imedia24.shop.domain.product.CreateProductDTO
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.UpdateProductDTO
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @Column(name = "sku", nullable = false)
    val sku: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @Column(name = "stock", nullable = false)
    val stock: Int,

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime
){

    constructor() :this(" "," ", "",BigDecimal.ZERO, 0,ZonedDateTime.now(),ZonedDateTime.now())


    fun toDto(): ProductResponse = ProductResponse(
        sku = this.sku,
        name = this.name,
        description = this.description ?: "",
        price = this.price,
        stock = this.stock
    )

    companion object {
        fun fromDto(dto: CreateProductDTO) = ProductEntity(
            sku = dto.sku,
            name = dto.name,
            description = dto.description ?: "",
            price = dto.price,
            stock = dto.stock,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )

        fun fromDto(dto: UpdateProductDTO, existingProduct: ProductEntity) = ProductEntity(
            sku = existingProduct.sku,
            name = dto.name ?: existingProduct.name,
            description = dto.description ?: existingProduct.description,
            price = dto.price ?: existingProduct.price,
            stock = dto.stock ?: existingProduct.stock,
            createdAt = existingProduct.createdAt,
            updatedAt = ZonedDateTime.now())
    }

}
