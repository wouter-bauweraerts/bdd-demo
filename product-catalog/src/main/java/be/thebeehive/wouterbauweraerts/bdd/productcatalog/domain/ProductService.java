package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequest;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.AddProductResponse;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.exception.ProductNotFoundException;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    public ProductDto getProduct(int productId) {
        return productRepository.findById(productId)
                .map(productMapper::map)
                .orElseThrow(() -> ProductNotFoundException.withProductId(productId));
    }

    @Transactional
    public AddProductResponse addProduct(AddProductRequest request) {
        Product newProduct = productRepository.save(
                productMapper.map(request)
        );
        return new AddProductResponse(newProduct.getId());
    }
}
