package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;

@Mapper(componentModel = SPRING)
public interface ProductMapper {
    @Mapping(source = "id", target = "id", defaultValue = "-1")
    ProductDto map(Product product);
}
