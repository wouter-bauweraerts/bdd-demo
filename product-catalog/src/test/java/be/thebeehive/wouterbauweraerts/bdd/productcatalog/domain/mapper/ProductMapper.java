package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.Mapper;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;

@Mapper(componentModel = SPRING)
public interface ProductMapper {
    ProductDto map(Product product);
}
