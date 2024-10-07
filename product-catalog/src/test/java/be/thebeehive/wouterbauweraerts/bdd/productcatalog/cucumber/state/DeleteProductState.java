package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class DeleteProductState {
    private Integer productId;

}
