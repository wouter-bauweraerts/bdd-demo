package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.common.api.response.ServerErrorResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.state.HttpResponseState;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateOrderSteps extends AbstractStepDefinition {

    private static final String API_ORDERS = "/api/orders";
    private final HttpResponseState statusState;

    protected CreateOrderSteps(TestRestTemplate restTemplate, HttpResponseState statusState) {
        super(restTemplate);
        this.statusState = statusState;
    }

    @When("I try to create an order with product {int}")
    public void createOrderWithNonExistingProduct(int productId) {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .withOrderlines(Map.of(productId, 1))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }
}
