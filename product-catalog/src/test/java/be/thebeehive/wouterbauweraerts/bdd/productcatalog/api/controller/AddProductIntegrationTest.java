package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequest;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.AddProductResponse;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AddProductIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/data/delete-products.sql")
    void addProduct_addsProductToDatabase_andReturnsId() throws Exception {
        AddProductRequest request = AddProductRequestFixtures.anAddProductRequest();

        String addProductResponseJson = mockMvc.perform(
                        post("/api/product-overview")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AddProductResponse response = objectMapper.readValue(addProductResponseJson, AddProductResponse.class);

        assertThat(response).isNotNull()
                .extracting(AddProductResponse::productId)
                .isNotNull();

        assertThat(productRepository.findById(response.productId()))
                .isNotEmpty()
                .hasValueSatisfying(product -> assertThat(product)
                        .returns(request.brand(), Product::getBrand)
                        .returns(request.type(), Product::getType)
                );
    }

    @Test
    void addProduct_invalidRequest_returnsExpected() throws Exception {
        AddProductRequest request = AddProductRequestFixtures.fixtureBuilder()
                .ignoreType()
                .build();

        mockMvc.perform(
                        post("/api/product-overview")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request content."));
    }
}
