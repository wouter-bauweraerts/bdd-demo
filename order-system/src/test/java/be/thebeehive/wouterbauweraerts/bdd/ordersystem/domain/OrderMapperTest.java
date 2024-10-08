package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.instancio.junit.InstancioSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.Orderline;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.OrderlineFixtures;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.ProductFixtures;

class OrderMapperTest {
    OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @ParameterizedTest
    @MethodSource("mapOrderLineSource")
    void mapOrderline_mapsExpected(Orderline in, Product product, OrderlineEntity out) {
        assertThat(mapper.mapOrderline(in, product)).usingRecursiveComparison()
                .isEqualTo(out);
    }

    public static Stream<Arguments> mapOrderLineSource() {
        return Stream.of(
                Arguments.of(null, null, null),
                createMapOrderlineArguments(null, ProductFixtures.aProduct()),
                createMapOrderlineArguments(OrderlineFixtures.anOrderline(), null),
                createMapOrderlineArguments(OrderlineFixtures.anOrderline(), ProductFixtures.aProduct())

        );
    }

    private static Arguments createMapOrderlineArguments(Orderline orderline, Product product) {
        return Arguments.of(
                orderline,
                product,
                OrderlineEntity.builder()
                        .productId(extractOrDefault(orderline, Orderline::productId, null))
                        .brand(extractOrDefault(product, Product::brand, null))
                        .type(extractOrDefault(product, Product::type, null))
                        .quantity(extractOrDefault(orderline, Orderline::quantity, null))
                        .build()
        );
    }

    @ParameterizedTest
    @InstancioSource
    void mapOrder(CreateOrderRequest request, List<OrderlineEntity> orderlines) {
        assertThat(mapper.mapOrder(request, orderlines)).usingRecursiveComparison()
                .isEqualTo(
                        Order.builder()
                                .customerId(request.customerId())
                                .orderlines(orderlines)
                                .build()
                );
    }

    private static <T, R> R extractOrDefault(T value, Function<T, R> getter, R defaultValue) {
        return ofNullable(value)
                .map(getter)
                .orElse(defaultValue);
    }


}