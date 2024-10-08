package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Orderline(@NotNull Integer productId, @NotNull @Positive Integer quantity) {
}
