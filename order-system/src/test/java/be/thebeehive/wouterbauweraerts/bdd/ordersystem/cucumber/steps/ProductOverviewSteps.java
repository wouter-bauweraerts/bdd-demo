package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductOverviewSteps {
    private final ProductCatalogClient pcClient;

    @Given("product {int} does not exist")
    public void productProductIdDoesNotExist(int productId) {
        when(pcClient.getProduct(anyInt())).thenReturn(empty());
    }
}
