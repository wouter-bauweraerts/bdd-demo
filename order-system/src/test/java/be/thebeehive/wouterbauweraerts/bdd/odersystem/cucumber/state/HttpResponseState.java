package be.thebeehive.wouterbauweraerts.bdd.odersystem.cucumber.state;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class HttpResponseState {
    private String message;
    private HttpStatusCode statusCode;
}
