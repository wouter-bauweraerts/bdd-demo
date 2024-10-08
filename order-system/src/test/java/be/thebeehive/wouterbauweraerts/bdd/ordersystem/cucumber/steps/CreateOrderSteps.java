package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.shaded.org.apache.commons.lang3.tuple.Triple;

import be.thebeehive.wouterbauweraerts.bdd.common.api.response.ServerErrorResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.OrderlineFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.response.CreateOrderResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.state.CreateOrderState;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.state.HttpResponseState;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain.Order;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain.OrderlineEntity;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.repository.OrderRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateOrderSteps extends AbstractStepDefinition {

    private static final String API_ORDERS = "/api/orders";
    private final HttpResponseState statusState;
    private final CreateOrderState createOrderState;
    private final OrderRepository orderRepository;
    private final ProductCatalogClient productCatalogClient;

    protected CreateOrderSteps(TestRestTemplate restTemplate, HttpResponseState statusState, CreateOrderState createOrderState, OrderRepository orderRepository, ProductCatalogClient productCatalogClient) {
        super(restTemplate);
        this.statusState = statusState;
        this.createOrderState = createOrderState;
        this.orderRepository = orderRepository;
        this.productCatalogClient = productCatalogClient;
    }

    @Given("product {int} does not exist")
    public void productProductIdDoesNotExist(int productId) {
        when(productCatalogClient.getProduct(anyInt())).thenReturn(empty());
    }

    @Given("product {int} exists")
    public void productExists(int productId) {
        when(productCatalogClient.getProduct(productId)).thenReturn(
                Optional.of(ProductFixtures.aProduct())
        );
    }

    @Given("A valid request with single product")
    public void setupValidRequestWithSingleProduct() {
        CreateOrderRequest request = CreateOrderRequestFixtures.aCreateOrderRequestWithSingleProduct();
        Map<Integer, Triple<Integer, Product, Integer>> products = request.orderlines().stream()
                .map(it -> Triple.of(it.productId(), ProductFixtures.aProduct(), it.quantity()))
                .collect(Collectors.toMap(Triple::getLeft, Function.identity()));

        products.forEach((key, value) -> when(productCatalogClient.getProduct(key)).thenReturn(Optional.of(value.getMiddle())));
        createOrderState.setRequest(request);
        createOrderState.setExpectedOrderlines(
                products.values().stream()
                        .map(it -> new OrderlineEntity(it.getLeft(), it.getMiddle().brand(), it.getMiddle().type(), it.getRight()))
                        .toList()
        );
    }

    @Given("a valid request with multiple products")
    public void setupValidRequestWithMultipleProducts() {
        CreateOrderRequest request = CreateOrderRequestFixtures.aCreateOrderRequest();
        Map<Integer, Triple<Integer, Product, Integer>> products = request.orderlines().stream()
                .map(it -> Triple.of(it.productId(), ProductFixtures.aProduct(), it.quantity()))
                .collect(Collectors.toMap(Triple::getLeft, Function.identity()));

        products.forEach((key, value) -> when(productCatalogClient.getProduct(key)).thenReturn(Optional.of(value.getMiddle())));
        createOrderState.setRequest(request);
        createOrderState.setExpectedOrderlines(
                products.values().stream()
                        .map(it -> new OrderlineEntity(it.getLeft(), it.getMiddle().brand(), it.getMiddle().type(), it.getRight()))
                        .toList()
        );
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

    @When("I try to create an order")
    public void validCreateOrderWithSingleOrderline() {
        ResponseEntity<CreateOrderResponse> response = restTemplate.postForEntity(API_ORDERS, createOrderState.getRequest(), CreateOrderResponse.class);

        statusState.setStatusCode(response.getStatusCode());
        createOrderState.setOrderId(response.getBody().orderId());
        createOrderState.setCustomerId(createOrderState.getRequest().customerId());
    }

    @Then("The order is created")
    public void theOrderIsCreated() {
        assertThat(createOrderState.getOrderId()).isNotNull();
        assertThat(orderRepository.findById(createOrderState.getOrderId())).hasValueSatisfying(order -> {
            assertThat(order).returns(createOrderState.getCustomerId(), Order::getCustomerId);
            assertThat(order.getOrderlines()).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrderElementsOf(createOrderState.getExpectedOrderlines());
        });
    }
}
