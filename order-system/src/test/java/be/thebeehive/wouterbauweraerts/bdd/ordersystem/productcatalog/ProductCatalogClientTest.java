package be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductCatalogClientException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.ProductFixtures;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCatalogClientTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(options().dynamicPort())
            .failOnUnmatchedRequests(false)
            .build();

    @DynamicPropertySource
    static void initializeProperties(DynamicPropertyRegistry registry) {
        registry.add("product-catalog.base-uri", wireMock::baseUrl);

        configureFor(wireMock.getPort());
    }

    @Autowired
    ProductCatalogClient client;
    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @InstancioSource
    void getProductWhenNotFoundReturnsEmptyOptional(Integer productId) {
        wireMock.stubFor(
                WireMock.get("/api/product-overview/%d".formatted(productId))
                        .willReturn(
                                aResponse()
                                        .withStatus(404)
                        )
        );

        assertThat(client.getProduct(productId)).isEmpty();

        verify(getRequestedFor(urlEqualTo("/api/product-overview/%d".formatted(productId))));
    }

    @ParameterizedTest
    @InstancioSource
    void getProductWhenException(Integer productId) {
        wireMock.stubFor(
                WireMock.get("/api/product-overview/%d".formatted(productId))
                        .willReturn(
                                aResponse()
                                        .withStatus(409)
                        )
        );

        assertThatThrownBy(() -> client.getProduct(productId))
                .isInstanceOf(ProductCatalogClientException.class)
                .hasMessage("Unable to retrieve product details")
                .hasCauseInstanceOf(HttpClientErrorException.class);

        verify(getRequestedFor(urlEqualTo("/api/product-overview/%d".formatted(productId))));
    }

    @ParameterizedTest
    @InstancioSource
    void getProductSuccess(Integer productId) throws Exception {
        Product product = ProductFixtures.aProduct();
        wireMock.stubFor(
                WireMock.get("/api/product-overview/%d".formatted(productId))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                                        .withBody(objectMapper.writeValueAsString(product))
                        )
        );
        assertThat(client.getProduct(productId)).isPresent()
                        .hasValue(product);

        verify(getRequestedFor(urlEqualTo("/api/product-overview/%d".formatted(productId))));

    }
}