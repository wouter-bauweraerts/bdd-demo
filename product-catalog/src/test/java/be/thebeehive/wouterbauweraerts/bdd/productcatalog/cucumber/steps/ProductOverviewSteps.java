package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

import java.util.Comparator;
import java.util.Objects;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.HttpStatusState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.ProductOverviewState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.Products;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.util.JacksonPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductOverviewSteps extends AbstractStepDefinition {
    private static final String API_PRODUCT_OVERVIEW = "/api/product-overview";

    private static final ParameterizedTypeReference<JacksonPage<ProductDto>> PAGE_PRODUCT_DTO_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    private final Products products;
    private final HttpStatusState statusInformation;
    private final ProductOverviewState productOverviewState;

    public ProductOverviewSteps(TestRestTemplate restTemplate, ProductMapper productMapper, HttpStatusState statusInformation, Products products, ProductOverviewState productOverviewState) {
        super(restTemplate, productMapper);
        this.statusInformation = statusInformation;
        this.products = products;
        this.productOverviewState = productOverviewState;
    }

    @When("I retrieve products without pagination parameters")
    public void retrieveProducts() {
        ResponseEntity<JacksonPage<ProductDto>> response = getProductOverview(API_PRODUCT_OVERVIEW);

        statusInformation.setStatusCode(response.getStatusCode());
        productOverviewState.setActual(Objects.requireNonNull(response.getBody()).toList());
        productOverviewState.setExpected(
                products.getProducts().stream()
                        .sorted(Comparator.comparing(Product::getId))
                        .limit(10)
                        .map(productMapper::map)
                        .toList()
        );
    }

    @When("I retrieve page {int} with size {int} of the product overview")
    public void iRetrievePageWithSizeOfTheProductOverview(int page, int size) {
        ResponseEntity<JacksonPage<ProductDto>> response = getProductOverview(productOverviewWithPagination(page, size));

        statusInformation.setStatusCode(response.getStatusCode());
        productOverviewState.setActual(Objects.requireNonNull(response.getBody()).toList());
        productOverviewState.setExpected(
                products.getProducts().stream()
                        .sorted(Comparator.comparing(Product::getId))
                        .skip((long) page * size)
                        .limit(size)
                        .map(productMapper::map)
                        .toList()
        );
    }

    @Then("I receive the expected page of products")
    public void verifyPageContent() {
        assertThat(productOverviewState.getActual()).containsExactlyInAnyOrderElementsOf(productOverviewState.getExpected());
    }

    private static String productOverviewWithPagination(int page, int size) {
        return API_PRODUCT_OVERVIEW + "?page=" + page + "&size=" + size;
    }

    private ResponseEntity<JacksonPage<ProductDto>> getProductOverview(String request) {
        return restTemplate.exchange(
                request,
                GET,
                null,
                PAGE_PRODUCT_DTO_TYPE_REF);
    }
}
