package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import java.util.List;
import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import be.thebeehive.wouterbauweraerts.bdd.common.api.response.ServerErrorResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.OrderlineFixtures;
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
                .withOrderlines(List.of(OrderlineFixtures.orderline(productId, 1)))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }

    @When("I try to create an order without customerId for product {int}")
    public void createOrderWithoutCustomerId(int productId) {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .ignoreCustomerId()
                .withOrderlines(List.of(OrderlineFixtures.orderline(productId, 1)))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }

    @When("I try to create an order without products")
    public void createOrderWithoutProducts() {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .withOrderlines(List.of(OrderlineFixtures.orderline(null, 1)))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }

    @When("I try to create an order without quantity")
    public void iTryToCreateAnOrderWithoutQuantity() {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .withOrderlines(List.of(OrderlineFixtures.orderline(1, null)))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }

    @When("I try to create an order with quantity {int}")
    public void iTryToCreateAnOrderWithQuantityQuantity(int quantity) {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .withOrderlines(List.of(OrderlineFixtures.orderline(1, quantity)))
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }

    @When("I try to create an order without orderlines")
    public void iTryToCreateAnOrderWithoutOrderlines() {
        CreateOrderRequest request = CreateOrderRequestFixtures.fixtureBuilder()
                .ignoreOrderlines()
                .build();

        ResponseEntity<ServerErrorResponse> response = restTemplate.postForEntity(API_ORDERS, request, ServerErrorResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        statusState.setMessage(response.getBody().message());
    }
}
