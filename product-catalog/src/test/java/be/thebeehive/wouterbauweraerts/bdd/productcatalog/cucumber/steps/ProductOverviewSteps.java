package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static be.thebeehive.wouterbauweraerts.bdd.productcatalog.assertions.HttpStatusAssert.assertStatusCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.springframework.http.HttpMethod.GET;

import java.util.Comparator;
import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.CucumberSpringConfiguration;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.util.JacksonPage;
import io.cucumber.java.After;
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
    @Autowired
    ProductMapper productMapper;

    private List<Product> products;
    private JacksonPage<ProductDto> result;
    private List<ProductDto> expectedProducts;
    private HttpStatusCode statusCode;

    @After
    public void cleanup() {
        productRepository.deleteAll();
    }

    @Given("existing products")
    public void setupExistingProducts() {
        log.info("Setup existing products");
        products = Instancio.ofList(ProductFixtures.PRODUCT_MODEL)
                .size(50)
                .ignore(field(Product::getId))
                .create();

        products = productRepository.saveAll(products);
        log.info("Products saved");
    }

    @Then("I receive the expected page of products")
    public void verifyFirstPage() {
        assertStatusCode(statusCode).isNotNull().is2xxCode();
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedProducts);
    }

    @When("I retrieve products without pagination parameters")
    public void retrieveProducts() {
        log.debug("Retrieving products (without page parameter)");
        ResponseEntity<JacksonPage<ProductDto>> response =getProductOverview(API_PRODUCT_OVERVIEW);

        statusCode = response.getStatusCode();
        result = response.getBody();
        expectedProducts = products.stream()
                .sorted(Comparator.comparing(Product::getId))
                .limit(10)
                .map(productMapper::map)
                .toList();
    }

    @When("I retrieve page {int} with size {int} of the product overview")
    public void iRetrievePageWithSizeOfTheProductOverview(int page, int size) {
        log.debug("Retrieving products (with page parameter: page = %d, size = %d)".formatted(page, size));
        ResponseEntity<JacksonPage<ProductDto>> response = getProductOverview(productOverviewWithPagination(page, size));

        statusCode = response.getStatusCode();
        result = response.getBody();
        expectedProducts = products.stream()
                .sorted(Comparator.comparing(Product::getId))
                .skip((long)page * size)
                .limit(size)
                .map(productMapper::map)
                .toList();
    }

    private static final String productOverviewWithPagination(int page, int size) {
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
