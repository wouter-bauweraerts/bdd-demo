package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.thebeehive.wouterbauweraerts.bdd.common.dummy.DummyPage;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductOverviewIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ProductMapper productMapper;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/data/delete-products.sql", "/data/test-products.sql"})
    void retrieveProductOverviewWithourPaginationParametersReturnsExpectedProducts() throws Exception {
        TypeReference<DummyPage<ProductDto>> pageTypeRef = new TypeReference<>() {};

        String jsonResponse = mockMvc.perform(
                        get("/api/product-overview")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductDto> actual = objectMapper.readValue(jsonResponse, pageTypeRef).content();

        assertThat(actual).hasSize(10)
                .containsExactlyElementsOf(filterProducts(0, 10));
    }

    @ParameterizedTest
    @MethodSource("pagedOverviewSource")
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/data/delete-products.sql", "/data/test-products.sql"})
    void retrieveProductOverviewReturnsExpectedProducts(int page, int pageSize) throws Exception {
        TypeReference<DummyPage<ProductDto>> pageTypeRef = new TypeReference<>() {};

        String jsonResponse = mockMvc.perform(
                        get("/api/product-overview?page=%d&size=%d".formatted(page, pageSize))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductDto> actual = objectMapper.readValue(jsonResponse, pageTypeRef).content();

        assertThat(actual).hasSize(pageSize)
                .containsExactlyElementsOf(filterProducts(page, pageSize));
    }

    private static Stream<Arguments> pagedOverviewSource() {
        return Stream.of(
                Arguments.of(0, 10),
                Arguments.of(1, 10),
                Arguments.of(0, 20)
        );
    }

    private List<ProductDto> filterProducts(int page, int size) {
        return productRepository.findAll()
                .stream()
                .skip((long) page * size)
                .limit(size)
                .map(productMapper::map)
                .toList();
    }


}