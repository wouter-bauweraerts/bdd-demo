package be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog;

import java.util.Optional;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;

@Component
public class ProductCatalogClient {
    public Optional<Product> getProduct(int productId) {
        return Optional.empty();
    }
}
