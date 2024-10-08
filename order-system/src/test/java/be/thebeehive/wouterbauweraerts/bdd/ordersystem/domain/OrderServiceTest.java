package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.util.Pair;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductNotFoundException;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequestFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.Orderline;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.response.CreateOrderResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.ProductFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    ProductCatalogClient pcClient;
    @Mock
    OrderRepository orderRepository;
    @Spy
    OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void createOrderWhenProductNotFoundThrowsExpected() {
        CreateOrderRequest request = CreateOrderRequestFixtures.aCreateOrderRequestWithSingleProduct();
        when(pcClient.getProduct(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product %s not found".formatted(
                        request.orderlines()
                                .stream()
                                .findFirst()
                                .map(Orderline::productId)
                                .orElse(-1)
                ));

        verifyNoInteractions(mapper);
    }

    @Test
    void createOrderWithSingleProduct() {
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        int createdOrderId = Instancio.create(Integer.class);
        CreateOrderRequest request = CreateOrderRequestFixtures.aCreateOrderRequestWithSingleProduct();
        Product product = ProductFixtures.aProduct();

        List<OrderlineEntity> expectedOrderlines = createOrderlines(
                request.orderlines().stream()
                        .collect(Collectors.toMap(
                                Orderline::productId,
                                ol -> Pair.of(product, ol)))
        );

        when(pcClient.getProduct(anyInt())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocationOnMock -> new Order(
                createdOrderId,
                invocationOnMock.getArgument(0, Order.class).getCustomerId(),
                invocationOnMock.getArgument(0, Order.class).getOrderlines()
        ));

        assertThat(orderService.createOrder(request)).returns(createdOrderId, CreateOrderResponse::orderId);
        verify(orderRepository).save(orderCaptor.capture());

        assertThat(orderCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(
                        Order.builder()
                                .customerId(request.customerId())
                                .orderlines(expectedOrderlines)
                );
    }

    private static List<OrderlineEntity> createOrderlines(Map<Integer, Pair<Product, Orderline>> data) {
        return data.values().stream()
                .map((Pair<Product, Orderline> pair) -> new OrderlineEntity(
                        pair.getSecond().productId(),
                        pair.getFirst().brand(),
                        pair.getFirst().type(),
                        pair.getSecond().quantity()
                )).toList();
    }
}