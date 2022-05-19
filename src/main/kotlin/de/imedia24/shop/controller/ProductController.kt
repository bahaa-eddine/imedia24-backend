package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.CreateProductDTO
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.UpdateProductDTO
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }


    @GetMapping("/products", produces = ["application/json"])
    fun findProductsBySku(
        @RequestParam(name = "skus") skus: String
    ): ResponseEntity<List<ProductResponse>> {
        logger.info("Retrieving products")
        val products = productService.findProductsBySku(skus)
        return if(products.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }
    @PostMapping("/products", produces = ["application/json"])
    fun addNewProduct(@RequestBody product: CreateProductDTO): ResponseEntity<ProductResponse> {
        logger.info("Request to add a product")
        val persistedProduct = productService.addProduct(product);
        if (ObjectUtils.isEmpty(persistedProduct)) {
            return ResponseEntity<ProductResponse>(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(persistedProduct, HttpStatus.CREATED)
    }

    @PutMapping("/products/{sku}", produces = ["application/json"])
    fun updateProduct(@PathVariable("sku") sku: String, @RequestBody product: UpdateProductDTO) : ResponseEntity<ProductResponse> {
        logger.info("Request to update product: {}", sku)
        val updatedProduct = productService.updateProduct(sku, product)
        return if (updatedProduct != null) {
            ResponseEntity.ok().body(updatedProduct)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
