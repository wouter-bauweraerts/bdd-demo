package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteProductIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void deleteNonExistingProductReturnsExpected() throws Exception {
        Integer productId;

        do {
            productId = Instancio.create(Integer.class);
        } while (productRepository.existsById(productId));

        mockMvc.perform(
                delete("/api/product-overview/%d".formatted(productId))
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteExistingProductReturnsExpected() throws Exception {
        int productId = productRepository.save(
                ProductFixtures.fixtureBuilder()
                        .ignoreId()
                        .build()
        ).getId();

        mockMvc.perform(
                delete("/api/product-overview/%d".formatted(productId))
        ).andExpect(status().isNoContent());

        assertThat(productRepository.existsById(productId)).isFalse();
    }
}
