package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.Orderline;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto.Product;

@Mapper(componentModel = SPRING)
public interface OrderMapper {
    OrderlineEntity mapOrderline(Orderline orderline, Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderlines", source = "orderlines")
    Order mapOrder(CreateOrderRequest request, List<OrderlineEntity> orderlines);
}
