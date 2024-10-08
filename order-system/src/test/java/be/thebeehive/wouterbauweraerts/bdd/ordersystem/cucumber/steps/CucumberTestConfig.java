package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.ProductCatalogClient;
import io.cucumber.spring.CucumberContextConfiguration;

@SuppressWarnings("unused")
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CucumberTestConfig {
    @MockBean
    ProductCatalogClient pcClient;
}
