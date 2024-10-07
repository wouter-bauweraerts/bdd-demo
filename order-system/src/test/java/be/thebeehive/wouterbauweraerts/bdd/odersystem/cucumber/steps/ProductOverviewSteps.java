package be.thebeehive.wouterbauweraerts.bdd.odersystem.cucumber.steps;

import be.thebeehive.wouterbauweraerts.bdd.odersystem.util.ProductOverviewStub;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductOverviewSteps {
    private final ProductOverviewStub productOverviewStub;

    @Given("product {int} does not exist")
    public void productProductIdDoesNotExist(int productId) {
        productOverviewStub.setupNonExistingProduct(productId);
    }
}
