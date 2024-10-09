package be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductCatalogClientException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;

@Component
public class ProductCatalogClient {
    private static final String PRODUCT_BY_ID_URL = "/api/product-overview/{productId}";

    private final RestTemplate restTemplate;
    private final String productCatalogBaseUri;

    public ProductCatalogClient(RestTemplate restTemplate, @Value("${product-catalog.base-uri}") String productCatalogBaseUri) {
        this.restTemplate = restTemplate;
        this.productCatalogBaseUri = productCatalogBaseUri;
    }

    public Optional<Product> getProduct(int productId) {
        try {
            URI getProductUri = UriComponentsBuilder.fromHttpUrl(productCatalogBaseUri)
                    .path(PRODUCT_BY_ID_URL)
                    .uriVariables(Map.of("productId", productId))
                    .build()
                    .toUri();
            Product product = restTemplate.getForObject(getProductUri, Product.class);
            return Optional.ofNullable(product);
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProductCatalogClientException(ex);
        }
    }
}
