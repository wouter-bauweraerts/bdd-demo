package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class ProductCatalogClientException extends RuntimeException {
    public ProductCatalogClientException(HttpStatusCodeException cause) {
        super("Unable to retrieve product details", cause);
    }
}
