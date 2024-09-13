package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import java.util.List;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class ProductOverviewState {
    private List<ProductDto> expected;
    private List<ProductDto> actual;
}
