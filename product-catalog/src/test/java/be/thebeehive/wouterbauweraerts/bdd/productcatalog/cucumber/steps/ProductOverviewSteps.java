package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static be.thebeehive.wouterbauweraerts.bdd.productcatalog.assertions.HttpStatusAssert.assertStatusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;

import org.instancio.Instancio;
import org.instancio.InstancioCollectionsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.assertions.HttpStatusAssert;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.CucumberSpringConfiguration;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.util.JacksonPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CucumberContextConfiguration
public class ProductOverviewSteps extends CucumberSpringConfiguration {
    private static final ParameterizedTypeReference<JacksonPage<ProductDto>> PAGE_PRODUCT_DTO_TYPE_REF = new ParameterizedTypeReference<>() {};
    private static final String API_PRODUCT_OVERVIEW = "/api/product-overview";

    @Autowired
    ProductRepository productRepository;

    private JacksonPage<ProductDto> result;

    @Given("existing products")
    public void setupExistingProducts() {
        log.info("Setup existing products");
        List<Product> products = Instancio.ofList(ProductFixtures.PRODUCT_MODEL)
                .size(50)
                .ignore(field(Product::getId))
                .create();

        products = productRepository.saveAll(products);
        log.info("Products saved");
    }

    @When("I retrieve products")
    public void retrieveProducts() {
        log.debug("Retrieving products (without page parameter)");
        ResponseEntity<JacksonPage<ProductDto>> response = restTemplate.exchange(API_PRODUCT_OVERVIEW, GET, null, PAGE_PRODUCT_DTO_TYPE_REF);

        assertStatusCode(response.getStatusCode()).is2xxCode();
        result = response.getBody();
    }

    @Then("I receive the first page of products")
    public void verifyFirstPage() {
        log.info("Verify first page of products");
    }
}
