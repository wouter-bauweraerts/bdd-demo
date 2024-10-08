package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException withProductId(int productId) {
        return new ProductNotFoundException("Product %d not found".formatted(productId));
    }
}
