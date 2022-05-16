package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.CreateProductDTO
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.UpdateProductDTO
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? = productRepository.findBySku(sku)?.toProductResponse()

    fun findProductsBySku(skus: List<String>?): List<ProductResponse> {
        val products = mutableListOf<ProductResponse>()
        skus?.forEach{
            productRepository.findBySku(it)?.let { product -> products.add(product.toProductResponse()) }
        }
        return products;
    }

    fun addProduct(product: CreateProductDTO): ProductResponse = productRepository.save(ProductEntity.fromDto(product)).toDto()

    fun updateProduct(sku: String, product: UpdateProductDTO): ProductResponse? {

        val currentProduct = productRepository.findBySku(sku)
        return if (currentProduct != null) productRepository.save(ProductEntity.fromDto(product, currentProduct)).toDto()
        else null
    }
}
