package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.DeleteProductState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.HttpStatusState;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteProductSteps extends AbstractStepDefinition {
    private final HttpStatusState statusState;
    private final DeleteProductState deleteState;
    private final ProductRepository productRepository;

    public DeleteProductSteps(TestRestTemplate restTemplate, ProductMapper productMapper, HttpStatusState statusState, DeleteProductState deleteState, ProductRepository productRepository) {
        super(restTemplate, productMapper);
        this.statusState = statusState;
        this.deleteState = deleteState;
        this.productRepository = productRepository;
    }

    @When("I delete a product")
    public void deleteExistingProduct() {
        Integer productId = productRepository.findAll()
                .stream()
                .map(Product::getId)
                .findAny()
                .orElseThrow(() -> new RuntimeException("No products available"));

        deleteState.setProductId(productId);

        ResponseEntity<Void> response = restTemplate.exchange("/api/product-overview/{productId}", HttpMethod.DELETE, null, Void.class, productId);
        statusState.setStatusCode(response.getStatusCode());
    }

    @When("I delete a non existing product")
    public void deleteNonExistingProduct() {
        Integer productId;

        do {
            productId = abs(Instancio.create(Integer.class));
        } while (productRepository.existsById(productId));

        deleteState.setProductId(productId);

        ResponseEntity<Void> response = restTemplate.exchange("/api/product-overview/{productId}", HttpMethod.DELETE, null, Void.class, productId);
        statusState.setStatusCode(response.getStatusCode());
    }

    @Then("The product no longer exists in the system")
    public void productDoesNotExistInSystem() {
        assertThat(productRepository.existsById(deleteState.getProductId())).isFalse();
    }
}
