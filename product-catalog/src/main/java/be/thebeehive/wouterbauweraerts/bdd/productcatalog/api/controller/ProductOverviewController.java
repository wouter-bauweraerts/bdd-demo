package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests.AddProductRequest;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.AddProductResponse;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product-overview")
@RequiredArgsConstructor
public class ProductOverviewController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getProductOverview(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") int productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AddProductResponse addProduct(@RequestBody @Valid AddProductRequest request) {
        return productService.addProduct(request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
    }
}
