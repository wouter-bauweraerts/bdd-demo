package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.springframework.beans.factory.annotation.Autowired;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.Products;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class DataManagementSteps {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    Products products;

    @Given("existing products")
    public void setupExistingProducts() {
        products.setProducts(
                productRepository.saveAll(
                        Instancio.ofList(ProductFixtures.PRODUCT_MODEL)
                                .size(50)
                                .ignore(field(Product::getId))
                                .create()
                )
        );
    }

    @Before
    public void cleanup() {
        productRepository.deleteAll();
    }
}
