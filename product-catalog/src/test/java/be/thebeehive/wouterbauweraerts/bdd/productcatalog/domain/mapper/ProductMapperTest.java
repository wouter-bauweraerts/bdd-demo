package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mapstruct.factory.Mappers;
import org.testcontainers.shaded.org.apache.commons.lang3.tuple.Pair;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;

class ProductMapperTest {
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @TestFactory
    Stream<DynamicTest> mapProduct() {
        return Stream.of(
                        ProductFixtures.aProduct(),
                        ProductFixtures.fixtureBuilder()
                                .ignoreId()
                                .build(),
                        ProductFixtures.fixtureBuilder()
                                .ignoreBrand()
                                .build(),
                        ProductFixtures.fixtureBuilder()
                                .ignoreType()
                                .build()
                ).map(this::createPair)
                .map(it -> dynamicTest("ProductMapper maps %s to %s".formatted(it.getLeft().toString(), it.getRight().toString()), () -> {
                    assertThat(mapper.map(it.getLeft())).isEqualTo(it.getRight());
                }));
    }

    private Pair<Product, ProductDto> createPair(Product product) {
        return Pair.of(
                product,
                new ProductDto(getOrElse(product.getId(), -1), product.getBrand(), product.getType())
        );
    }

    private <T> T getOrElse(T value, T defaultValue) {
        return Objects.isNull(value) ? defaultValue : value;
    }
}