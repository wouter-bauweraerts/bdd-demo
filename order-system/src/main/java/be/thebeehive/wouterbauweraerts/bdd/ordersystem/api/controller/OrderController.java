package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.response.CreateOrderResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
