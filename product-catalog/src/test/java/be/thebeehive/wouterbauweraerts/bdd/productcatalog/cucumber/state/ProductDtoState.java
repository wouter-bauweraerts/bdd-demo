package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class ProductDtoState {
    private ProductDto expected;
    private ProductDto actual;
}
