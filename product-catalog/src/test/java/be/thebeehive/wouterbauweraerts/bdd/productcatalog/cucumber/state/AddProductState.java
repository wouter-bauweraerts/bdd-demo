package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class AddProductState {

    private String brand;
    private String type;
    private Integer productId;

    public void setProduct(Product productToAdd) {
        brand = productToAdd.getBrand();
        type = productToAdd.getType();
    }

    public void setId(Integer productId) {
        this.productId = productId;
    }
}
