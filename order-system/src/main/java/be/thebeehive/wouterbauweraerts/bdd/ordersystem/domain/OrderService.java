package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductNotFoundException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.Orderline;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.response.CreateOrderResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductCatalogClient productCatalogClient;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;


    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        List<OrderlineEntity> orderlines = request.orderlines()
                .stream()
                .map(this::enrichOrderLines)
                .toList();
        Order order = orderRepository.save(orderMapper.mapOrder(request, orderlines));
        return new CreateOrderResponse(order.getId());
    }

    private OrderlineEntity enrichOrderLines(Orderline orderline) {
        return productCatalogClient.getProduct(orderline.productId())
                .map(p -> orderMapper.mapOrderline(orderline, p))
                .orElseThrow(() -> ProductNotFoundException.withProductId(orderline.productId()));
    }
}
