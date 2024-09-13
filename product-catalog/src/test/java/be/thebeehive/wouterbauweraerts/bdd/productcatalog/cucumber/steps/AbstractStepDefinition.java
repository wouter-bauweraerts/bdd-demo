package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import org.springframework.boot.test.web.client.TestRestTemplate;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbstractStepDefinition {
    protected final TestRestTemplate restTemplate;
    protected final ProductMapper productMapper;
}
