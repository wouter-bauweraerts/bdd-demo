package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException withProductId(int productId) {
        return new ProductNotFoundException("Product with id %d not found".formatted(productId));
    }
}
