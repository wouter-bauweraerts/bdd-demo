package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(@NotNull Integer customerId,  @NotEmpty List<@Valid Orderline> orderlines) {
}
