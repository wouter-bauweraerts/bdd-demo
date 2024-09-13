package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain;

import static be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures.PRODUCT_MODEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.instancio.Instancio;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.util.JacksonPage;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Spy
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @TestFactory
    Stream<DynamicTest> getProductsReturnsEmptyPageWhenNothingReturnedFromRepository() {
        return Stream.of(
                Pageable.unpaged(),
                PageRequest.of(1,100),
                PageRequest.of(50,10)
        ).map(pr -> dynamicTest("empty page with given PageRequest: %S".formatted(pr.toString()), () -> {
            when(productRepository.findAll(pr)).thenReturn(Page.empty());

            assertThat(productService.getProducts(pr)).isEmpty();

            verifyNoInteractions(productMapper);

        }));
    }

    @Test
    void getProductsReturnsExpectedResults() {
        List<Product> products = Instancio.ofList(PRODUCT_MODEL).size(5).create();
        List<ProductDto> expectedDtos = products.stream()
                .map(it -> new ProductDto(it.getId(), it.getBrand(), it.getType()))
                .toList();

        when(productRepository.findAll(any(Pageable.class))).thenAnswer(a -> new JacksonPage<>(products, a.getArgument(0), products.size()));

        assertThat(productService.getProducts(Pageable.unpaged())).containsExactlyInAnyOrderElementsOf(expectedDtos);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productMapper, times(products.size())).map(productCaptor.capture());
        assertThat(productCaptor.getAllValues()).containsExactlyInAnyOrderElementsOf(products);
    }
}