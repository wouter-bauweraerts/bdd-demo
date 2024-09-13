package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::map);
    }
}
