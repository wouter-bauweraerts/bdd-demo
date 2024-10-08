package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductNotFoundException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    ProductCatalogClient pcClient;

    @Test
    void createOrderWhenProductNotFoundThrowsExpected() {
        CreateOrderRequest request = CreateOrderRequestFixtures.aCreateOrderRequestWithSingleProduct();
        when(pcClient.getProduct(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product %s not found".formatted(
                        request.orderLines().keySet()
                                .stream()
                                .findFirst()
                                .orElse(-1)
                ));
    }
}