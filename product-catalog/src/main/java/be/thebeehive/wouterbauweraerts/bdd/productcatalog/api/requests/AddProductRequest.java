package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests;

import jakarta.validation.constraints.NotBlank;

public record AddProductRequest(
        @NotBlank String brand,
        @NotBlank String type
) {
}
