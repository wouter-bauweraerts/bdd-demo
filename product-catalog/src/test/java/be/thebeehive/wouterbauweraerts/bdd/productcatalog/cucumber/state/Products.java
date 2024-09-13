package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class Products {
    List<Product> products;

    public int getExistingProductId() {
        return products.stream()
                .findAny()
                .map(Product::getId)
                .orElseThrow();
    }

    public int getNonExistingProductId() {
        Random random = new Random(459);
        int nonExistingId;
        do {
            nonExistingId = random.nextInt(5000);
        } while (productIdDoesNotExist(nonExistingId));

        return nonExistingId;
    }

    private boolean productIdDoesNotExist(int nonExistingId) {
        return products.stream().anyMatch(p -> p.getId().equals(nonExistingId));
    }
}
