package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import org.flywaydb.core.internal.util.Pair;
import org.springframework.stereotype.Service;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductNotFoundException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.response.CreateOrderResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductCatalogClient productCatalogClient;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        request.orderLines().entrySet()
                .stream()
                .map(e -> Pair.of(e.getKey(), e.getValue()))
                .map(this::enrichOrderLines)
                .toList();
        return new CreateOrderResponse(-1);
    }

    private Pair<Product, Integer> enrichOrderLines(Pair<Integer, Integer> orderline) {
        return productCatalogClient.getProduct(orderline.getLeft())
                .map(p -> Pair.of(p, orderline.getRight()))
                .orElseThrow(() -> ProductNotFoundException.withProductId(orderline.getLeft()));
    }
}
