package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class HttpStatusState {
    HttpStatusCode statusCode;
}
