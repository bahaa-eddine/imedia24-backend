package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.util.*

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mvc:MockMvc

    @MockBean
    lateinit var productService: ProductService

    @Test
    fun findProductsBySkus_success(){
        val skus = Arrays.asList("123", "8574", "8901", "2345", "67789")
        Mockito.`when`(productService.findProductsBySku(skus)).thenReturn(buildProductList())

        mvc.perform(MockMvcRequestBuilders
            .get("/products")
            .queryParam("skus", skus.joinToString(","))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isNotEmpty)
            .andExpect(jsonPath("$[0].name").value("123")
            )

    }

    fun buildProductList():List<ProductResponse>{
        val product_1 =  ProductResponse("123", "123", "123", BigDecimal(100),10);
        val product_2 =  ProductResponse("456", "456", "456", BigDecimal(200),20);
        return Arrays.asList(product_1,product_2);

    }

}
