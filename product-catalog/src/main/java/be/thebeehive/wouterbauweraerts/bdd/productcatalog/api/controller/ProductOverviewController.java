package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductService;
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
}
