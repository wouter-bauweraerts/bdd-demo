package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request;

import java.util.Map;

public record CreateOrderRequest(Integer customerId, Map<Integer, Integer> orderLines) {
}
