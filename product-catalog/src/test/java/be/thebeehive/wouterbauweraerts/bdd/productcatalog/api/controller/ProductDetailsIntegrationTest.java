package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductDetailsIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/data/delete-products.sql"})
    void retrieveExistingProductsReturnsExpectedStatusAndProduct() throws Exception {
        Product product = productRepository.save(
                ProductFixtures.fixtureBuilder()
                        .ignoreId()
                        .build()
        );
        mockMvc.perform(
                        get("/api/product-overview/%d".formatted(product.getId()))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.brand").value(product.getBrand()))
                .andExpect(jsonPath("$.type").value(product.getType()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/data/delete-products.sql"})
    void retrieveNonExistingProductsReturnsExpectedStatusAndProduct() throws Exception {

        mockMvc.perform(
                        get("/api/product-overview/%d".formatted(Instancio.create(Integer.class)))
                ).andExpect(status().isNotFound());
    }
}
