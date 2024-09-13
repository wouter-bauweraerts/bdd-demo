package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.mapper.ProductMapper;
import be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {
    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ProductMapper productMapper;

    protected List<Product> products;
    protected HttpStatusCode statusCode;
}
