package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static be.thebeehive.wouterbauweraerts.bdd.common.assertions.HttpStatusAssert.assertStatusCode;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.common.api.response.ServerErrorResponse;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequest;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.AddProductResponse;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.AddProductState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.HttpStatusState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddProductSteps extends AbstractStepDefinition {
    private static final String API_PRODUCT_OVERVIEW = "/api/product-overview";

    private final HttpStatusState statusState;
    private final AddProductState addProductState;

    public AddProductSteps(TestRestTemplate restTemplate, ProductMapper productMapper, HttpStatusState statusState, AddProductState addProductState) {
        super(restTemplate, productMapper);
        this.statusState = statusState;
        this.addProductState = addProductState;
    }

    @Given("a valid add product request")
    public void aValidAddProductRequest() {
        addProductState.setProduct(
                ProductFixtures.fixtureBuilder()
                        .ignoreId()
                        .build()
        );
    }

    @When("I add the product")
    public void performAddProduct() {
        AddProductRequest addProductRequest = new AddProductRequest(
                addProductState.getBrand(),
                addProductState.getType()
        );

        ResponseEntity<AddProductResponse> response = restTemplate.postForEntity(
                API_PRODUCT_OVERVIEW,
                addProductRequest,
                AddProductResponse.class
        );

        statusState.setStatusCode(response.getStatusCode());
        addProductState.setId(response.getBody().productId());
    }

    @When("I add the invalid product")
    public void performInvalidAddProduct() {
        AddProductRequest addProductRequest = new AddProductRequest(
                addProductState.getBrand(),
                addProductState.getType()
        );

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(
                API_PRODUCT_OVERVIEW,
                addProductRequest,
                ServerErrorResponse.class
        );

        statusState.setStatusCode(response.getStatusCode());
        statusState.setErrorMessage(response.getBody().message());
    }

    @Then("the productId is returned")
    public void theProductIdIsReturned() {
        assertThat(addProductState.getProductId()).isNotNull();
    }

    @Then("the product can be retrieved")
    public void verifyProductCanBeRetrieved() {
        ResponseEntity<ProductDto> response = restTemplate.getForEntity(
                "%s/%d".formatted(API_PRODUCT_OVERVIEW, addProductState.getProductId()),
                ProductDto.class
        );

        assertStatusCode(response.getStatusCode().value()).isStatus(200);
        assertThat(response.getBody()).isNotNull()
                .returns(addProductState.getBrand(), ProductDto::brand)
                .returns(addProductState.getType(), ProductDto::type);
    }

    @Given("an invalid add product request")
    public void anInvalidAddProductRequest() {
        AddProductRequest request = AddProductRequestFixtures.fixtureBuilder()
                .ignoreBrand()
                .build();

        addProductState.setBrand(request.brand());
        addProductState.setType(request.type());
    }
}
