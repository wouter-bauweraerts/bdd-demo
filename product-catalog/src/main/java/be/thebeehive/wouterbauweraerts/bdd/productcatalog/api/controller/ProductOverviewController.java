package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.response.ProductDto;

@RestController
@RequestMapping("/api/product-overview")
public class ProductOverviewController {
    @GetMapping
    public Page<ProductDto> getProductOverview(Pageable pageable) {
        return Page.empty();
    }
}
