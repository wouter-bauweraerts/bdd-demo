package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.HttpStatusState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.ProductDtoState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.Products;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductDetailSteps extends AbstractStepDefinition {
    private static final String API_PRODUCT_DETAIL = "/api/product-overview/%d";

    private final Products products;
    private final HttpStatusState statusInformation;
    private final ProductDtoState productDtoState;

    public ProductDetailSteps(TestRestTemplate restTemplate, ProductMapper productMapper, Products products, HttpStatusState statusInformation, ProductDtoState productDtoState) {
        super(restTemplate, productMapper);
        this.products = products;
        this.statusInformation = statusInformation;
        this.productDtoState = productDtoState;
    }

    @When("I retrieve a product with a non-existing ID")
    public void requestNonExistingProduct() {
        int nonExistingId = products.getNonExistingProductId();

        statusInformation.setStatusCode(getProduct(nonExistingId).getStatusCode());
    }

    @When("I retrieve a product with an existing ID")
    public void retrieveExistingProduct() {
        int existingId = products.getExistingProductId();

        ResponseEntity<ProductDto> productResponse = getProduct(existingId);
        statusInformation.setStatusCode(productResponse.getStatusCode());
        productDtoState.setExpected(
                products.getProducts().stream().filter(p -> p.getId().equals(existingId))
                        .findFirst()
                        .map(productMapper::map)
                        .orElseThrow()
        );
        productDtoState.setActual(productResponse.getBody());
    }

    @Then("I receive the expected product")
    public void validateProduct() {
        assertThat(productDtoState.getActual()).isEqualTo(productDtoState.getExpected());
    }

    private ResponseEntity<ProductDto> getProduct(int id) {
        return restTemplate.exchange(
                API_PRODUCT_DETAIL.formatted(id),
                GET,
                null,
                ProductDto.class);
    }
}
